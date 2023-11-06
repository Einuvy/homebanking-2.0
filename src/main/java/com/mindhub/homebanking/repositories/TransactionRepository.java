package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.subModels.Client;
import com.mindhub.homebanking.models.superModels.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
}