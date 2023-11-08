package com.mindhub.homebanking.models.DTO.request;

import com.mindhub.homebanking.models.ENUM.CardColor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class CardRegisterDTO {
    @NotNull(message = "Number is required")
    private Double creditLimit;
    private CardColor cardColor;
}
