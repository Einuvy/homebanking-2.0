package com.mindhub.homebanking.models.DTO.request;

import com.mindhub.homebanking.models.ENUM.CardColor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class CardRegisterDTO {
    @Email
    private String email;
    @NotNull
    private Double creditLimit;
    private CardColor cardColor;
}
