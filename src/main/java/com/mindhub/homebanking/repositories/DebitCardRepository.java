package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.ENUM.CardColor;
import com.mindhub.homebanking.models.subModels.CheckingAccount;
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

    void deleteByAccount(CheckingAccount account);

    boolean existsByColorAndAccount_Client_EmailAndAccount_NumberStartsWithAndActiveTrue(CardColor color, String email, String number);

    Optional<DebitCard> findByNumberAndAccountClientEmailAndActiveTrue(String number, String email);
}