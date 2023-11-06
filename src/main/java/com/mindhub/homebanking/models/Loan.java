package com.mindhub.homebanking.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE loan SET active = false WHERE id = ?")
public class Loan {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Setter
    @Column(unique = true)
    private String code;

    @Setter
    private String name;

    @Setter
    private String description;

    @Setter
    private Double interestRate;

    @Setter
    private Integer maxAmount;

    @Setter
    private Integer minAmount;

    @Setter
    private Boolean active = true;

    @Setter
    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    @Setter
    @ElementCollection
    private List<Integer> paymentsPeriods = List.of(5, 10, 15, 20);

    @OneToMany(mappedBy = "loan")
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan(String code,String name, String description, double interestRate, int maxAmount, int minAmount, List<Integer> payments) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.interestRate = interestRate;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.payments = payments;
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }
}
