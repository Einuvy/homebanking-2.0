package com.mindhub.homebanking.models.ENUM;

import lombok.Getter;

@Getter
public enum AccountType {
    CHECKING("MHBC"),
    SAVING("MHBS");

    private final String abbreviation;

    private AccountType(String abbreviation) {
        this.abbreviation = abbreviation;
    }


}
