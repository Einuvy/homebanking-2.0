package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.subModels.Client;
import com.mindhub.homebanking.models.subModels.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount, Long> {
    List<SavingAccount> findByClientAndActiveTrue(Client client);
}
