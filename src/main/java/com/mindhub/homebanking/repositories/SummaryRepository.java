package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Summary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {

    boolean existsByCode(String code);

    Optional<Summary> findByCode(String code);


    List<Summary> findByCreditCardNumber(String number);

    List<Summary> findByCreditCard_Client_EmailAndPurchases_CreditCard_Number(String email, String number, Pageable pageable);


}