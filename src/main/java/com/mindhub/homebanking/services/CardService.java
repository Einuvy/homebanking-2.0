package com.mindhub.homebanking.services;


import com.mindhub.homebanking.models.DTO.response.CardDTO;
import com.mindhub.homebanking.models.ENUM.CardColor;
import com.mindhub.homebanking.models.subModels.Client;
import com.mindhub.homebanking.models.subModels.CreditCard;
import com.mindhub.homebanking.models.subModels.DebitCard;
import com.mindhub.homebanking.models.superModels.Card;

import java.util.List;

public interface CardService {

    //CREATE
    CardDTO createCreditCard(CardColor cardColor, Double creditLimit, Client client);

    DebitCard createDebitCard(CardColor cardColor);

    //READ
    List<Card> findAllCards();

    List<Card> findAllCardsByClientEmail(String email);

    Card findCardById(Long id);

    Card findCardByNumber(String number);

    //READ DTO
    List<CardDTO> findAllDTOCards();

    List<CardDTO> findAllCardsDTOByClientEmail(String email);

    CardDTO findCardDTOById(Long id);

    CardDTO findCardDTOByNumber(String number);

    //UPDATE
    DebitCard saveDebitCard(DebitCard debitCard);

    CreditCard saveCreditCard(CreditCard creditCard);

    //DELETE
    void deleteCard(Card card);
}
