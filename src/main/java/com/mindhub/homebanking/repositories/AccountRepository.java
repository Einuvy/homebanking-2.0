package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.superModels.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(String number);
    boolean existsByAlias(String alias);
    boolean existsByNumber(String number);
    boolean existsByCBU(String cbu);
    Optional<Account> findByNumberAndActiveTrue(String number);
    Optional<Account> findByIdAndActiveTrue(long id);
    Optional<Account> findByCBUAndActiveTrue(String cbu);
}