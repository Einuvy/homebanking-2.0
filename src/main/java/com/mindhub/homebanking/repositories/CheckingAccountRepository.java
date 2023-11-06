package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.subModels.CheckingAccount;
import com.mindhub.homebanking.models.subModels.Client;
import com.mindhub.homebanking.models.superModels.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
    List<CheckingAccount> findByClientAndActiveTrue(Client client);
}