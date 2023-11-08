package com.mindhub.homebanking.models.DTO.request;

import com.mindhub.homebanking.validation.Annotation.MinLessThanMax;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@MinLessThanMax
public class LoanCreationDTO {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "Name must be alphanumeric and between 3 and 20 characters")
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Interest rate cannot be null")
    @Min(value = 0, message = "Interest rate must be greater than 0")
    private Double interestRate;

    @NotNull(message = "Max amount cannot be null")
    private Integer maxAmount;

    @NotNull(message = "Min amount cannot be null")
    private Integer minAmount;

    @NotNull(message = "Payments cannot be null")
    private List<Integer> payments;


}
