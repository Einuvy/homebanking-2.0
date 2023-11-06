package com.mindhub.homebanking.models.DTO.response;

import com.mindhub.homebanking.models.superModels.Person;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class PersonAuthDTO {
    private final Long id;

    private final String firstName;

    private final String lastName;

    private final String email;

    private final LocalDate birthDate;

    private final String dni;

    private final LocalDate creationDate;

    private final String role;

    @Setter
    private String token;

    public PersonAuthDTO(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.birthDate = person.getBirthDate();
        this.dni = person.getDni();
        this.creationDate = person.getCreationDate();
        this.role = person.getRole().toString();
    }
}
