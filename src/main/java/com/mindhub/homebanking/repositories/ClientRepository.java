package com.mindhub.homebanking.repositories;


import com.mindhub.homebanking.models.subModels.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    //Clients
    Optional<Client> findByEmailAndActiveTrue(String email);
    boolean existsByEmailIgnoreCaseAndActiveTrue(String email);
    boolean existsByDniAndActiveTrue(String dni);
    Optional<Client> findByIdAndActiveTrue(long id);
    List<Client> findByActiveTrue();

    // Employees/Managers
    boolean existsByDni(String dni);
    boolean existsByEmailIgnoreCase(String email);
}
