package com.mindhub.homebanking.models.DTO.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class AccountPatchDTO {
    @NotBlank(message = "Alias is required")
    @Pattern(regexp = "^[A-Za-z]{3,12}\\.[A-Za-z]{3,12}$", message = "Alias must be between 3 and 12 characters long for part and must be in the format: name.lastname")
    private String alias;
    @NotBlank(message = "Number is required")
    private String number;
}
