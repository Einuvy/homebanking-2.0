package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.DTO.request.TransactionCreateDTO;
import com.mindhub.homebanking.models.DTO.response.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> findAllByAccountNumber(String number);

    List<TransactionDTO> findAllDTOByAccountNumber(String number);

    TransactionDTO createTransaction(TransactionCreateDTO transactionCreateDTO);

    Transaction saveTransaction(Transaction transaction);
}
