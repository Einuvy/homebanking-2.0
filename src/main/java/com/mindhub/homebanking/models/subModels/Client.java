package com.mindhub.homebanking.models.subModels;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.superModels.Person;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.mindhub.homebanking.models.ENUM.RoleType.ROLE_CLIENT;
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
//El completo solo funciona en postgresql
/*+
                 "UPDATE checking_account SET active = false WHERE client_id = ?;"+
                 "UPDATE saving_account SET active = false WHERE client_id = ?;"+
                 "UPDATE credit_card SET active = false WHERE client_id = ?;"*/
/*@SQLDelete(sql = "UPDATE client SET active = false WHERE id = ?")*/
public class Client extends Person {

    @OneToMany(mappedBy = "client")
    private Set<CheckingAccount> checkingAccounts = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<SavingAccount> savingAccounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = EAGER)
    private Set<CreditCard> creditCards = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Client() {
        super(ROLE_CLIENT);
    }

    public Client(String firstName, String lastName, String email, String password, LocalDate birthDate, String dni) {
        super(firstName, lastName, email, password, birthDate, dni, ROLE_CLIENT);
    }

    public void addAccountChecking(CheckingAccount checkingAccount){
        checkingAccount.setClient(this);
        checkingAccounts.add(checkingAccount);
    }
    public void addAccountSaving(SavingAccount accountSaving){
        accountSaving.setClient(this);
        savingAccounts.add(accountSaving);
    }

    public void addCreditCard(CreditCard creditCard){
        creditCard.setClient(this);
        creditCards.add(creditCard);
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }

}
