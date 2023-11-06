package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.superModels.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByEmailIgnoreCaseAndActiveTrue(String email);

    boolean existsByDniAndActiveTrue(String dni);

    Optional<Person> findByEmailAndActiveTrue(String email);
}