package com.mindhub.homebanking.models.subModels;

import com.mindhub.homebanking.models.superModels.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
/*
@SQLDelete(sql = "UPDATE account SET active = false WHERE id = ?")
*/
public class CheckingAccount extends Account {

    @OneToOne(mappedBy = "account")
    private DebitCard debitCard;

    @Setter
    @ManyToOne
    private Client client;

    public CheckingAccount(String number, String alias, String CBU) {
        super(number, alias, CBU);
    }

    public void addDebitCard(DebitCard debitCard) {
        this.debitCard = debitCard;
        debitCard.setAccount(this);
    }
}
