package com.mindhub.homebanking.models.subModels;

import com.mindhub.homebanking.models.ENUM.RoleType;
import com.mindhub.homebanking.models.superModels.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

import static com.mindhub.homebanking.models.ENUM.RoleType.ROLE_ADMIN;

@Entity
@Getter
/*@SQLDelete(sql = "UPDATE employee SET active = false WHERE id = ?")*/
public class Employee extends Person {

    public Employee() {
        super(ROLE_ADMIN);
    }

    @Setter
    private Boolean active = true;
    public Employee(String firstName, String lastName, String email, String password, LocalDate birthDate, String dni) {
        super(firstName, lastName, email, password, birthDate, dni, ROLE_ADMIN);
    }
}
