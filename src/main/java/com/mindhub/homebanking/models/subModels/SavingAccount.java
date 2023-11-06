package com.mindhub.homebanking.models.subModels;

import com.mindhub.homebanking.models.superModels.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
/*
@SQLDelete(sql = "UPDATE saving_account SET active = false WHERE id = ?")
*/
public class SavingAccount extends Account {
    @Setter
    @ManyToOne
    private Client client;

    private Double interestRate = 0.1d;

    private Double minimumBalance = 1000d;

    private Double maintenanceFee = 50d;

    private Double interest = 0d;

    private Double maxWithdrawal = 100000d;

    public SavingAccount(String number, String alias, String CBU) {
        super(number, alias, CBU);
    }

    public void addInterest() {
        this.interest = super.getAmount() * this.interestRate;
        super.setAmount(super.getAmount() + this.interest);
    }

    public void subtractMaintenanceFee() {
        super.setAmount(super.getAmount() - this.maintenanceFee);
    }

}
