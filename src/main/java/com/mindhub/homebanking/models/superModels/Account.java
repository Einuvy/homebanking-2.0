package com.mindhub.homebanking.models.superModels;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.subModels.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.JOINED;

@Entity
@Inheritance(strategy = JOINED)
@Getter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Setter
    private LocalDateTime creationDate = LocalDateTime.now();

    @Setter
    @Column(unique = true)
    private String number;

    @Setter
    private Double amount=0.0;

    @Setter
    private Boolean active = true;

    @Setter
    @Column(unique = true)
    private String alias;

    @Setter
    @Column(unique = true)
    private String CBU;

    @Setter
    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions = new HashSet<>();

    @Setter
    @ManyToOne
    private Client client;

    public Account(String number, String alias, String CBU) {
        this.number = number;
        this.alias = alias;
        this.CBU = CBU;
    }

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public void subtractAmount(Double amount){
        this.amount -= amount;
    }

    public void addAmount(Double amount){
        this.amount += amount;
    }

}
