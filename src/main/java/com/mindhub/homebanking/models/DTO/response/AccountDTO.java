package com.mindhub.homebanking.models.DTO.response;

import com.mindhub.homebanking.models.superModels.Account;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountDTO {
    private final LocalDateTime creationDate;
    private final String number;
    private final Double amount;
    private final String alias;
    private final String CBU;

    public AccountDTO(Account account) {
        this.creationDate = account.getCreationDate();
        this.number = account.getNumber();
        this.amount = account.getAmount();
        this.alias = account.getAlias();
        this.CBU = account.getCBU();
    }
}
