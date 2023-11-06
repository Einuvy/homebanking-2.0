package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.subModels.DebitCard;
import com.mindhub.homebanking.models.superModels.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebitCardRepository extends CardRepository<DebitCard, Long> {
    List<DebitCard> findByActiveTrue();
    List<DebitCard> findByAccount_Client_EmailAndActiveTrue(String email);

    Optional<Card> findByNumberAndActiveTrue(String number);

    boolean existsByIdAndActiveTrue(Long aLong);
}