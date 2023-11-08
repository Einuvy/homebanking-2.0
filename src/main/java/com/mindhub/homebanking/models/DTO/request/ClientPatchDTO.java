package com.mindhub.homebanking.models.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
public class ClientPatchDTO {
    @NotBlank(message = "FirstName is required")
    private String firstName;
    @NotBlank(message = "LastName is required")
    private String lastName;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&#.$($)$-$_])[A-Za-z\\d$@$!%*?&#.$($)$-$_]{8,}$", message = "Password should have at least 8 characters, one uppercase, one lowercase, one number and one special character")
    private String password;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Birth date should be in the past")
    private LocalDate birthDate;

    @Pattern(regexp = "^\\d{7,8}$" , message = "DNI should have 7 or 8 digits")
    private String dni;
}
