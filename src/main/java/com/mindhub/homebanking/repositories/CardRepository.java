package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.superModels.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CardRepository<T extends Card, ID> extends JpaRepository<T, ID> {
    Optional<Card> findByIdAndActiveTrue(Long id);


}