package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.models.Purchase;
import com.mindhub.homebanking.models.Summary;
import com.mindhub.homebanking.models.subModels.CreditCard;
import com.mindhub.homebanking.models.superModels.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CreditCardRepository;
import com.mindhub.homebanking.repositories.PurchaseRepository;
import com.mindhub.homebanking.repositories.SummaryRepository;
import com.mindhub.homebanking.services.SummaryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


import static com.mindhub.homebanking.utils.SummaryUtils.summaryCode;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SummaryServiceImpl implements SummaryService {

    private CreditCardRepository creditCardRepository;
    private SummaryRepository summaryRepository;
    private PurchaseRepository purchaseRepository;
    private final AccountRepository accountRepository;

    public SummaryServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Summary getSummary(String code) {
        return summaryRepository.findByCode(code).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Summary not found"));
    }

    public List<Summary> getSummaryByCreditCardNumber(String number) {
        return summaryRepository.findByCreditCardNumber(number);
    }

    public List<Summary> getSummaryByCreditCardNumberAndClientEmail(String number, String email, Integer page) {
        return summaryRepository.findByCreditCard_Client_EmailAndPurchases_CreditCard_Number(email, number, PageRequest.of(page, 5));
    }

    public void paySummary(String code, String number, String email) {
        Account account = accountRepository.findByNumberAndClient_Email(number, email).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Account not found"));
        Summary summary = getSummary(code);
        account.subtractAmount(summary.getAmount());
        summary.setPaid(true);
        summary.getPurchases().forEach(purchase -> purchase.setPaid(true));
        summaryRepository.save(summary);
    }

    @Scheduled(cron = "0 0 0 25 * *") // 0 0 0 25 * * = 12:00 AM on the 25th day of every month
    public void generateMonthlySummaries() {
        List<CreditCard> creditCards = creditCardRepository.findByActiveTrue();
        LocalDateTime date = LocalDateTime.now();
        for (CreditCard creditCard : creditCards) {
            List<Purchase> purchases = purchaseRepository.findByCreditCardNumberAndCreationDateBeforeAndPaidFalse(creditCard.getNumber(), date);
            double totalAmount = 0.0;

            for (Purchase purchase : purchases) {
                totalAmount += purchase.getAmount();
            }

            if (totalAmount > 0.0) {
                String code;
                do {
                     code = summaryCode(date);
                } while (summaryRepository.existsByCode(code));

                Summary summary = new Summary(totalAmount, date.getMonth() + "/" + date.getYear() +" Monthly Summary", code);
                summary.setPurchases(new HashSet<>(purchases));
                summaryRepository.save(summary);
                purchases.forEach(purchase -> purchase.getSummaries().add(summary));
                purchaseRepository.saveAll(purchases);
                summary.setCreditCard(creditCard);
                summaryRepository.save(summary);
            }
        }
    }
}
