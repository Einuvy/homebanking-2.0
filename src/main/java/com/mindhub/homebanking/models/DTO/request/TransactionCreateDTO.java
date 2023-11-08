package com.mindhub.homebanking.models.DTO.request;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class TransactionCreateDTO {
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;
    private String description;
    @NotBlank(message = "Account number is required")
    private String AccountNumber;
    @NotBlank(message = "To account number is required")
    private String toAccountNumber;
}
