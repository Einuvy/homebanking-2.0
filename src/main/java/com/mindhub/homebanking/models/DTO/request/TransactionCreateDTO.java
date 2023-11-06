package com.mindhub.homebanking.models.DTO.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class TransactionCreateDTO {
    @NotNull
    private Double amount;
    private String description;
    @NotBlank
    private String AccountNumber;
    @NotBlank
    private String toAccountNumber;
}
