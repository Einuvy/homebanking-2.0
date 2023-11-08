package com.mindhub.homebanking.models.DTO.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class LoanPaymentDTO {
    @NotBlank(message = "Code cannot be blank")
    private String code;
    @NotBlank(message = "Account number cannot be blank")
    private String accountNumber;
}
