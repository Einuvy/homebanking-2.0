package com.mindhub.homebanking.models;

import com.mindhub.homebanking.models.subModels.CreditCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
public class Purchase {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Setter
    private Double amount;

    @Setter
    @Column(unique = true)
    private String code;

    @Setter
    private Integer maxPayments;

    @Setter
    private Integer currentPayment=0;

    @Setter
    private LocalDateTime creationDate = LocalDateTime.now();

    @Setter
    private String description;

    @ManyToOne
    @Setter
    private CreditCard creditCard;

    @ManyToMany(mappedBy = "purchases")
    @Setter
    private Set<Summary> summaries = new HashSet<>();

    @Setter
    private Boolean paid = false;

    public Purchase(Double amount,
                    Integer maxPayments,
                    String description,
                    String code) {
        this.amount = amount;
        this.maxPayments = maxPayments;
        this.description = description;
        this.code = code;
    }
}
