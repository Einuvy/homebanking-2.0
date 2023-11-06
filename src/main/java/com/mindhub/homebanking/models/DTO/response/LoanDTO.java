package com.mindhub.homebanking.models.DTO.response;

import com.mindhub.homebanking.models.Loan;
import lombok.Getter;

import java.util.List;

@Getter
public class LoanDTO {
    private final String code;
    private final String name;
    private final String description;
    private final Double interestRate;
    private final Integer maxAmount;
    private final Integer minAmount;
    private final List<Integer> payments;

    public LoanDTO(Loan loan) {
        this.code = loan.getCode();
        this.name = loan.getName();
        this.description = loan.getDescription();
        this.interestRate = loan.getInterestRate();
        this.maxAmount = loan.getMaxAmount();
        this.minAmount = loan.getMinAmount();
        this.payments = loan.getPayments();
    }
}
