package com.mindhub.homebanking.models.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegisterDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&#.$($)$-$_])[A-Za-z\\d$@$!%*?&#.$($)$-$_]{8,}$", message = "Password should have at least 8 characters, one uppercase, one lowercase, one number and one special character")
    private String password;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Birth date should be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "DNI is required")
    @Pattern(regexp = "^\\d{7,8}$" , message = "DNI should have 7 or 8 digits")
    private String dni;
}
