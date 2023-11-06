package com.mindhub.homebanking.models.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Setter
@Getter
@AllArgsConstructor
public class ErrorDTO {
    private int status;
    private String message;
    private String error;

}
