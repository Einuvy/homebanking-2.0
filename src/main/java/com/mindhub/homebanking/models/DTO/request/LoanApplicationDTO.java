package com.mindhub.homebanking.models.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class LoanApplicationDTO {
    @NotBlank(message = "Email is required")
    private String code;
    @NotNull(message = "Amount is required")
    @Min(value = 10, message = "Amount should be greater")
    private Double amount;
    @NotNull(message = "Payments is required")
    private Integer payments;
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    @NotNull(message = "Payment period is required")
    private Integer paymentPeriod;

}
