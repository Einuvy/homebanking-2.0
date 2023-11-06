package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.models.DTO.request.TransactionCreateDTO;
import com.mindhub.homebanking.models.DTO.response.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.superModels.Account;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.ENUM.TransactionType.DEPOSIT;
import static com.mindhub.homebanking.models.ENUM.TransactionType.WITHDRAWAL;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountService accountService, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> findAllByAccountNumber(String number) {
        return transactionRepository.findByAccount(accountService.findAccountByNumberAndActiveTrue(number));
    }

    @Override
    public List<TransactionDTO> findAllDTOByAccountNumber(String number) {
        return findAllByAccountNumber(number).stream().map(TransactionDTO::new).collect(Collectors.toList());
    }


    @Override
    public TransactionDTO createTransaction(TransactionCreateDTO transactionCreateDTO) {
        Account account = accountService.findAccountByNumberAndActiveTrue(transactionCreateDTO.getAccountNumber());
        Account toAccount = accountService.findAccountByNumberAndActiveTrue(transactionCreateDTO.getToAccountNumber());

        Transaction deposit = new Transaction(transactionCreateDTO.getAmount(), transactionCreateDTO.getDescription(), DEPOSIT);
        Transaction withdrawal = new Transaction(transactionCreateDTO.getAmount(), transactionCreateDTO.getDescription(), WITHDRAWAL);

        account.subtractAmount(transactionCreateDTO.getAmount());
        toAccount.addAmount(transactionCreateDTO.getAmount());

        account.addTransaction(deposit);
        toAccount.addTransaction(withdrawal);
        accountService.saveAccount(account);
        accountService.saveAccount(toAccount);


        saveTransaction(withdrawal);

        return new TransactionDTO(saveTransaction(deposit));
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}
