package com.mindhub.homebanking.models;

import com.mindhub.homebanking.models.subModels.CreditCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
public class Summary {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Setter
    private String description;

    @Setter
    @Column(unique = true)
    private String code;

    @Setter
    private LocalDate creationDate = LocalDate.now();

    @Setter
    private LocalDate thruDate = LocalDate.now().plusDays(15);

    @ManyToMany(mappedBy = "summaries")
    @Setter
    private Set<Purchase> purchases = new HashSet<>();

    @Setter
    private Double amount;

    @Setter
    private Boolean paid = false;

    @ManyToOne
    @Setter
    private CreditCard creditCard;

    public Summary(Double amount, String description, String code) {
        this.amount = amount;
        this.description = description;
        this.code = code;
    }

}
