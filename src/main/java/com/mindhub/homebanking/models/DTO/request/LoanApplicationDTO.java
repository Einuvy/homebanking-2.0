package com.mindhub.homebanking.models.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class LoanApplicationDTO {
    @NotBlank
    private String code;
    @NotNull
    private Double amount;
    @NotNull
    private Integer payments;
    @Email
    private String email;
    @NotBlank
    private String accountNumber;
    @NotNull
    private Integer paymentPeriod;

}
