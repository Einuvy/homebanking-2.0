package com.mindhub.homebanking.models.subModels;

import com.mindhub.homebanking.models.ENUM.CardColor;
import com.mindhub.homebanking.models.Purchase;
import com.mindhub.homebanking.models.Summary;
import com.mindhub.homebanking.models.superModels.Card;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE credit_card SET active = false WHERE id = ?")
public class CreditCard extends Card {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @Setter
    private Client client;

    @Setter
    @Column(nullable = false)
    private Double maxAmount;

    @OneToMany(mappedBy = "creditCard")
    private Set<Purchase> purchases = new HashSet<>();

    @OneToMany(mappedBy = "creditCard")
    private Set<Summary> summaries = new HashSet<>();

    public CreditCard(CardColor color, String number, String cvv, Double maxAmount) {
        super(color, number, cvv);
        this.maxAmount = maxAmount;
    }

    public void addSummary(Summary summary) {
        summary.setCreditCard(this);
        summaries.add(summary);
    }

    public void addPurchase(Purchase purchase) {
        purchase.setCreditCard(this);
        purchases.add(purchase);
    }


}
