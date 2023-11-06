package com.mindhub.homebanking.models.superModels;

import com.mindhub.homebanking.models.ENUM.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.JOINED;

@Entity
@Inheritance(strategy = JOINED)
@Getter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String firstName;

    @Setter
    @Column(nullable = false)
    private String lastName;

    @Setter
    @Column(unique = true, updatable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false)
    private LocalDate birthDate;

    @Setter
    @Column(unique = true, nullable = false)
    private String dni;

    @Setter
    @Column(nullable = false)
    private LocalDate creationDate = LocalDate.now();

    @Setter
    @Enumerated(STRING)
    @ColumnDefault("'ROLE_CLIENT'")
    private RoleType role;

    @Setter
    private Boolean active = true;

    public Person(RoleType role) {
        this.role = role;
    }

    public Person(String firstName, String lastName, String email, String password, LocalDate birthDate, String dni, RoleType role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.dni = dni;
        this.role = role;
    }
}