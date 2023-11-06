package com.mindhub.homebanking.models.DTO.response;


import com.mindhub.homebanking.models.subModels.Client;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class ClientDTO {

    private final String firstName;

    private final String lastName;

    private final String email;

    private final LocalDate birthDate;

    private final String dni;

    private final LocalDate creationDate;

    public ClientDTO(Client client) {
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.birthDate = client.getBirthDate();
        this.dni = client.getDni();
        this.creationDate = client.getCreationDate();
    }
}
