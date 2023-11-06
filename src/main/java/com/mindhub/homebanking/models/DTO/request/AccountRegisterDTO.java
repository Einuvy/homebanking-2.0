package com.mindhub.homebanking.models.DTO.request;

import com.mindhub.homebanking.models.ENUM.AccountType;
import com.mindhub.homebanking.models.ENUM.CardColor;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class AccountRegisterDTO {
    AccountType accountType;
    @Email(message = "Email should be valid")
    String email;
    CardColor cardColor;
}
