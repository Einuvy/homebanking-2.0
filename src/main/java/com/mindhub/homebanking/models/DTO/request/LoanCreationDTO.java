package com.mindhub.homebanking.models.DTO.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
public class LoanCreationDTO {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$")
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double interestRate;

    @NotNull
    private Integer maxAmount;

    @NotNull
    private Integer minAmount;

    @NotNull
    private List<Integer> payments;


}
