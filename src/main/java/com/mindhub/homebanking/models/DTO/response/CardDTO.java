package com.mindhub.homebanking.models.DTO.response;

import com.mindhub.homebanking.models.superModels.Card;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CardDTO {
    private final String cardNumber;
    private final String cvv;
    private final LocalDateTime thruDate;
    private final LocalDate fromDate;

    public CardDTO(Card card) {
        this.cardNumber = card.getNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate().toLocalDate();
    }
}
