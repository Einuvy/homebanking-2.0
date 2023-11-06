package com.mindhub.homebanking.models;

import com.mindhub.homebanking.models.subModels.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE client_loan SET paid = true WHERE id = ?")
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Setter
    @Column(unique = true)
    private String code;

    @ManyToOne
    @Setter
    private Client client;

    @ManyToOne
    @Setter
    private Loan loan;

    @Setter
    private Integer payments;

    @Setter
    private Double amount;

    @Setter
    private Double amountToPay;

    @Setter
    private Integer paymentsPeriod;

    @Setter
    private Integer actualPayment = 0;

    @Setter
    private Boolean paid = false;

    private Double totalAmountToPay;

    @Setter
    private LocalDate requestDate = LocalDate.now();

    public ClientLoan(String code, Integer payments, Double amount, Double amountToPay, Integer paymentsPeriod) {
        this.code = code;
        this.payments = payments;
        this.amount = amount;
        totalAmountToPay = amountToPay;
        this.amountToPay = amountToPay;
        this.paymentsPeriod = paymentsPeriod;
    }

    public void addActualPayment(){
        this.actualPayment++;
    }

    public void sustracbAmountToPay(Double amount){
        this.amountToPay -= amount;
    }
}
