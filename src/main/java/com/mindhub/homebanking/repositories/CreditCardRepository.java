package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.ENUM.CardColor;
import com.mindhub.homebanking.models.subModels.CreditCard;
import com.mindhub.homebanking.models.superModels.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardRepository extends CardRepository<CreditCard, Long> {
    List<CreditCard> findByActiveTrue();
    List<CreditCard> findByClient_ActiveTrue();
    Optional<CreditCard> findByNumberAndActiveTrue(String number);

    List<CreditCard> findByClientEmailAndActiveTrue(String email);

    /*@Override
    default Optional<Card> findById(Long id) {
        return Optional.empty();
    }*/
    Optional<Card> findByIdAndActiveTrue(Long id);

    boolean existsByNumberAndActiveTrue(String number);

    boolean existsByColorAndClient_EmailAndActiveTrue(CardColor color, String email);

    boolean existsByNumberAndClientEmailAndActiveTrue(String number, String email);

}