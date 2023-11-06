package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.models.DTO.response.AccountDTO;
import com.mindhub.homebanking.models.DTO.request.AccountPatchDTO;
import com.mindhub.homebanking.models.ENUM.AccountType;
import com.mindhub.homebanking.models.ENUM.CardColor;
import com.mindhub.homebanking.models.subModels.*;
import com.mindhub.homebanking.models.superModels.Account;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.AccountUtils.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CheckingAccountRepository checkingAccountRepository;
    private final SavingAccountRepository savingAccountRepository;
    private final ClientRepository clientRepository;
    private final CardService cardService;

    public AccountServiceImpl(AccountRepository accountRepository,
                              CheckingAccountRepository checkingAccountRepository,
                              SavingAccountRepository savingAccountRepository,
                              ClientRepository clientRepository,
                              CardService cardService) {
        this.accountRepository = accountRepository;
        this.checkingAccountRepository = checkingAccountRepository;
        this.savingAccountRepository = savingAccountRepository;
        this.clientRepository = clientRepository;
        this.cardService = cardService;
    }

    //CREATE
    @Override
    public AccountDTO createAccount(AccountType accountType, String email, CardColor cardColor) {
        Client client = clientRepository.findByEmailAndActiveTrue(email).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Client not found"));
        String number;
        String alias;
        String CBU;

        do {
            number = getRandomAccountNumber(accountType.getAbbreviation());
        }while (accountRepository.existsByNumber(number));
        do {
            alias = getRandomAlias();
        }while (accountRepository.existsByAlias(alias));
        do {
            CBU = getRandomCBU();
        }while (accountRepository.existsByCBU(CBU));
        if (AccountType.CHECKING.equals(accountType)){
            CheckingAccount checkingAccount = new CheckingAccount(number, alias, CBU);
            checkingAccountRepository.save(checkingAccount);
            client.addAccountChecking(checkingAccount);
            DebitCard debitCard = cardService.createDebitCard(cardColor);


            checkingAccount.setDebitCard(debitCard);
            debitCard.setAccount(checkingAccount);
            cardService.saveDebitCard(debitCard);
            return new AccountDTO(checkingAccountRepository.save(checkingAccount));
        }
        SavingAccount savingAccount = new SavingAccount(number, alias, CBU);
        client.addAccountSaving(savingAccount);
        return new AccountDTO(savingAccountRepository.save(savingAccount));
    }

    //READ
    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> findAllAccountsByClient(Client client) {
        List<Account> accountList = new ArrayList<>();
        accountList.addAll(savingAccountRepository.findByClientAndActiveTrue(client));
        accountList.addAll(checkingAccountRepository.findByClientAndActiveTrue(client));
        return accountList;
    }

    @Override
    public Account findAccountByIdAndActiveTrue(Long id) {
        return accountRepository.findByIdAndActiveTrue(id).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Account not found"));
    }

    @Override
    public Account findAccountByNumberAndActiveTrue(String number) {
        return accountRepository.findByNumberAndActiveTrue(number).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Account not found"));
    }

    @Override
    public Account findAccountByCBUAndActiveTrue(String CBU) {
        return accountRepository.findByCBUAndActiveTrue(CBU).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Account not found"));
    }

    //READ DTO
    @Override
    public List<AccountDTO> findAllDTOAccounts() {
        return findAllAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> findAllAccountsDTOByClient(Client client) {
        return findAllAccountsByClient(client).stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public AccountDTO findAccountDTOByCBU(String CBU) {
        return new AccountDTO(findAccountByCBUAndActiveTrue(CBU));
    }

    @Override
    public AccountDTO findAccountDTOByNumber(String number) {
        return new AccountDTO(findAccountByNumberAndActiveTrue(number));
    }

    @Override
    public AccountDTO findAccountDTOById(Long id) {
        return new AccountDTO(findAccountByIdAndActiveTrue(id));
    }

    //UPDATE
    @Override
    public Account saveAccount(Account account) {
        if (account instanceof CheckingAccount){
            return checkingAccountRepository.save((CheckingAccount) account);
        }
        return savingAccountRepository.save((SavingAccount) account);
    }

    @Override
    public AccountDTO updateAccount(AccountPatchDTO accountDTO) {
        Account account = findAccountByNumberAndActiveTrue(accountDTO.getNumber());
        if (accountRepository.existsByAlias(accountDTO.getAlias())){
            throw new ResponseStatusException(BAD_REQUEST, "Alias already exists");
        }
        account.setAlias(accountDTO.getAlias().toLowerCase());
        return new AccountDTO(accountRepository.save(account));
    }

    //DELETE
    @Override
    public void deleteAccount(Account account) {
        if (account instanceof CheckingAccount){
            cardService.deleteCard(((CheckingAccount) account).getDebitCard());
        }
        account.setActive(false);
        saveAccount(account);
    }

    @Override
    public void deleteAccountById(Long id) {
        Account account = findAccountByIdAndActiveTrue(id);
        account.setActive(false);
        accountRepository.save(account);
    }

    @Override
    public void deleteAccountByNumber(String number) {
        Account account =  findAccountByNumberAndActiveTrue(number);
        account.setActive(false);
        if (account instanceof CheckingAccount){
            cardService.deleteCard(((CheckingAccount) account).getDebitCard());
        }
        accountRepository.save(account);
    }

}
