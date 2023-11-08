package com.mindhub.homebanking.models.DTO.response;

import com.mindhub.homebanking.models.subModels.Client;
import lombok.Getter;

@Getter
public class ClientBasicDTO {
    private final String fullName;
    private final String email;

    public ClientBasicDTO(Client client) {
        this.fullName = client.getFirstName() + " " + client.getLastName();
        this.email = client.getEmail();
    }
}
