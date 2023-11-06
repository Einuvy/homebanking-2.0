package com.mindhub.homebanking.models.DTO.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class LoanPaymentDTO {
    @NotNull
    private String code;
    @NotNull
    private String accountNumber;
}
