package com.mindhub.homebanking.models.DTO.response;

import com.mindhub.homebanking.models.Transaction;
import lombok.Getter;

@Getter
public class TransactionDTO {
    private final String date;
    private final Double balance;
    private final String description;
    private final String transactionType;

    public TransactionDTO(Transaction transaction){
        this.date = transaction.getDate().toString();
        this.balance = transaction.getBalance();
        this.description = transaction.getDescription();
        this.transactionType = transaction.getTransactionType().toString();
    }

}
