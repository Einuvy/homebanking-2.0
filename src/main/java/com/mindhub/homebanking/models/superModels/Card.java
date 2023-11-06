package com.mindhub.homebanking.models.superModels;

import com.mindhub.homebanking.models.ENUM.CardColor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;


@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class Card {

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private CardColor color;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(length = 4, nullable = false)
    private String cvv;

    private LocalDateTime thruDate = LocalDateTime.now().plusYears(5);

    private LocalDateTime fromDate = LocalDateTime.now();

    @Setter
    private Boolean active = true;

    public Card(CardColor color, String number, String cvv) {
        this.color = color;
        this.number = number;
        this.cvv = cvv;
    }
}
