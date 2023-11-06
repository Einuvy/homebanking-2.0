package com.mindhub.homebanking.models;

import com.mindhub.homebanking.models.ENUM.TransactionType;
import com.mindhub.homebanking.models.superModels.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Setter
    private LocalDateTime date = LocalDateTime.now();

    @Setter
    private double balance;

    @Setter
    private String description;

    @Setter
    @Enumerated(value = STRING)
    private TransactionType transactionType;

    @Setter
    @ManyToOne
    private Account account;

    public Transaction(double balance, String description, TransactionType transactionType) {
        this.balance = balance;
        this.description = description;
        this.transactionType = transactionType;
    }
}
