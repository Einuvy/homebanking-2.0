package com.mindhub.homebanking.models.DTO.response;

import com.mindhub.homebanking.models.ClientLoan;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class ClientLoanDTO {
    private final String code;
    private final String client;
    private final String loan;
    private final Integer payments;
    private final Double amount;
    private final Double amountToPay;
    private final Integer paymentsPeriod;
    private final Integer actualPayment;
    private final LocalDate requestDate;
    private final Double nextPayment;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.code = clientLoan.getCode();
        this.client = clientLoan.getClient().getFirstName() + " " + clientLoan.getClient().getLastName();
        this.loan = clientLoan.getLoan().getName();
        this.payments = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();
        this.amountToPay = clientLoan.getAmountToPay();
        this.paymentsPeriod = clientLoan.getPaymentsPeriod();
        this.actualPayment = clientLoan.getActualPayment();
        this.requestDate = clientLoan.getRequestDate();
        this.nextPayment = clientLoan.getTotalAmountToPay() / clientLoan.getPayments();
    }
}
