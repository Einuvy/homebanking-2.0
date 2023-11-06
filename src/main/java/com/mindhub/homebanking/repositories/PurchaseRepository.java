package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCreditCard_NumberAndCreationDateAfterAndCurrentPaymentLessThanAndMaxPaymentsGreaterThan(String number, LocalDateTime creationDate, Integer currentPayment, Integer maxPayments);

    List<Purchase> findByMaxPaymentsLessThanAndCurrentPaymentGreaterThan(Integer maxPayments, Integer currentPayment);

    List<Purchase> findByCurrentPaymentLessThan(Integer currentPayment);


    List<Purchase> findByCreditCardNumberAndCreationDateAfterAndCurrentPaymentLessThan(String number, LocalDateTime creationDate, Integer maxPayments);
    List<Purchase> findByCreditCardNumberAndCreationDateBeforeAndCurrentPaymentLessThan(String number, LocalDateTime creationDate, Integer maxPayments);

}