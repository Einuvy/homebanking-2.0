package com.mindhub.homebanking.models.subModels;

import com.mindhub.homebanking.models.ENUM.CardColor;
import com.mindhub.homebanking.models.superModels.Card;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE debit_card SET active = false WHERE id = ?")
public class DebitCard extends Card {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Setter
    @OneToOne
    private CheckingAccount account;

    public DebitCard(CardColor color, String number, String cvv) {
        super(color, number, cvv);
    }


}
