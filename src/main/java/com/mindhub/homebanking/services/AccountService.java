package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.DTO.response.AccountDTO;
import com.mindhub.homebanking.models.DTO.request.AccountPatchDTO;
import com.mindhub.homebanking.models.ENUM.AccountType;
import com.mindhub.homebanking.models.ENUM.CardColor;
import com.mindhub.homebanking.models.subModels.Client;
import com.mindhub.homebanking.models.superModels.Account;

import java.util.List;

public interface AccountService {


    //CREATE
    AccountDTO createAccount(AccountType accountType, String email, CardColor cardColor);

    //READ
    List<Account> findAllAccounts();

    List<Account> findAllAccountsByClient(Client client);

    Account findAccountByIdAndActiveTrue(Long id);

    Account findAccountByNumberAndActiveTrue(String number);

    Account findAccountByCBUAndActiveTrue(String CBU);

    //READ DTO
    List<AccountDTO> findAllDTOAccounts();

    List<AccountDTO> findAllAccountsDTOByClient(Client client);

    AccountDTO findAccountDTOByCBU(String CBU);

    AccountDTO findAccountDTOByNumber(String number);

    AccountDTO findAccountDTOById(Long id);

    //UPDATE
    Account saveAccount(Account account);

    AccountDTO updateAccount(AccountPatchDTO accountDTO, String email);

    //DELETE
    void deleteAccount(Account account);

    void deleteAccountById(Long id);

    void deleteAccountByNumber(String number, String email);
}
