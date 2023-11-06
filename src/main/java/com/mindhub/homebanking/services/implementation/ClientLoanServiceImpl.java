package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.DTO.request.LoanApplicationDTO;
import com.mindhub.homebanking.models.DTO.response.ClientLoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.subModels.Client;
import com.mindhub.homebanking.models.superModels.Account;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.services.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.ENUM.TransactionType.DEPOSIT;
import static com.mindhub.homebanking.models.ENUM.TransactionType.WITHDRAWAL;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {

    private final ClientLoanRepository clientLoanRepository;
    private final LoanService loanService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public ClientLoanServiceImpl(ClientLoanRepository clientLoanRepository, LoanService loanService, ClientService clientService, AccountService accountService, TransactionService transactionService) {
        this.clientLoanRepository = clientLoanRepository;
        this.loanService = loanService;
        this.clientService = clientService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }


    @Override
    public List<ClientLoan> getClientLoans() {
        return clientLoanRepository.findAll();
    }

    @Override
    public List<ClientLoan> getClientLoansActive() {
        return clientLoanRepository.findByPaidFalse();
    }
    @Override
    public List<ClientLoan> getClientLoanByLoan(String code) {
        return clientLoanRepository.findByLoan_Code(code);
    }

    @Override
    public List<ClientLoan> getClientLoanByClientActive(String email) {
        return clientLoanRepository.findByClient_EmailAndPaidFalse(email);
    }

    @Override
    public ClientLoan getClientLoan(String code) {
        return clientLoanRepository.findByCode(code).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Loan not found"));
    }

    @Override
    public List<ClientLoanDTO> getClientLoanDTOs() {
        return getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ClientLoanDTO> getClientLoanDTOsActive() {
        return getClientLoansActive().stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ClientLoanDTO> getClientLoanDTOsByLoan(String code) {
        return getClientLoanByLoan(code).stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ClientLoanDTO> getClientLoanDTOsByClientActive(String email) {
        return getClientLoanByClientActive(email).stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientLoanDTO getClientLoanDTO(String code) {
        return new ClientLoanDTO(getClientLoan(code));
    }

    @Override
    public ClientLoanDTO createClientLoan(LoanApplicationDTO loanApplicationDTO) {
        Loan loan = loanService.getLoan(loanApplicationDTO.getCode());
        if (loanApplicationDTO.getAmount()>loan.getMaxAmount() || loanApplicationDTO.getAmount()<loan.getMinAmount()){
            throw new ResponseStatusException(BAD_REQUEST, "Amount not valid");
        }
        if(loan.getPayments().stream().noneMatch(p -> p.equals(loanApplicationDTO.getPayments()))){
            throw new ResponseStatusException(BAD_REQUEST, "Payments not valid");
        }
        if(loan.getPaymentsPeriods().stream().noneMatch(p -> p.equals(loanApplicationDTO.getPaymentPeriod()))){
            throw new ResponseStatusException(BAD_REQUEST, "Payments period not valid");
        }

        Client client = clientService.findClientByEmail(loanApplicationDTO.getEmail());
        Account account = accountService.findAccountByNumberAndActiveTrue(loanApplicationDTO.getAccountNumber());


        ClientLoan clientLoan = new ClientLoan(
                loanApplicationDTO.getCode()+"-"+loanApplicationDTO.getAccountNumber(),
                loanApplicationDTO.getPayments(),
                loanApplicationDTO.getAmount(),
                loanApplicationDTO.getAmount()*loan.getInterestRate()/100,
                loanApplicationDTO.getPaymentPeriod());

        Transaction transaction = new Transaction(loanApplicationDTO.getAmount(),"Loan application",  WITHDRAWAL);
        transactionService.saveTransaction(transaction);


        account.addAmount(loanApplicationDTO.getAmount());

        account.addTransaction(transaction);
        accountService.saveAccount(account);

        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);

        clientService.saveClient(client);
        loanService.saveLoan(loan);



        return new ClientLoanDTO(saveClientLoan(clientLoan));
    }

    @Override
    public  ClientLoan saveClientLoan(ClientLoan clientLoan) {
        return clientLoanRepository.save(clientLoan);
    }

    @Override
    public void payClientLoan(ClientLoan clientLoan, String accountNumber) {
        if (clientLoan.getPaid()){
            throw new ResponseStatusException(BAD_REQUEST, "Loan already paid");
        }
        if (clientLoan.getActualPayment()>=clientLoan.getPayments()){
            clientLoanRepository.delete(clientLoan);
            throw new ResponseStatusException(BAD_REQUEST, "Loan already paid");
        }
        Account account = accountService.findAccountByNumberAndActiveTrue(accountNumber);
        if (account.getAmount()<(clientLoan.getAmountToPay()/clientLoan.getPayments())){
            throw new ResponseStatusException(BAD_REQUEST, "Insufficient funds");
        }
        Transaction transaction = new Transaction(clientLoan.getAmountToPay(),"Loan payment",  DEPOSIT);
        transactionService.saveTransaction(transaction);
        account.subtractAmount(clientLoan.getAmountToPay());
        account.addTransaction(transaction);
        accountService.saveAccount(account);
        clientLoan.addActualPayment();
        clientLoan.sustracbAmountToPay(clientLoan.getAmount());
        if (clientLoan.getActualPayment()>=clientLoan.getPayments() && clientLoan.getAmountToPay()<= 1){
            clientLoan.setPaid(true);
        }
        saveClientLoan(clientLoan);
    }
}
