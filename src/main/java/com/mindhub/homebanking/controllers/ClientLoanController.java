package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.configuration.UserPrincipal;
import com.mindhub.homebanking.models.DTO.request.LoanApplicationDTO;
import com.mindhub.homebanking.models.DTO.request.LoanPaymentDTO;
import com.mindhub.homebanking.models.DTO.response.ClientLoanDTO;
import com.mindhub.homebanking.services.ClientLoanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client-loans")
public class ClientLoanController {

    private final ClientLoanService clientLoanService;

    public ClientLoanController(ClientLoanService clientLoanService) {
        this.clientLoanService = clientLoanService;
    }

    @GetMapping
    public List<ClientLoanDTO> getClientLoans() {
        return clientLoanService.getClientLoanDTOsActive();
    }

    @GetMapping("/{code}")
    public ClientLoanDTO getClientLoan(@PathVariable String code) {
        return clientLoanService.getClientLoanDTO(code);
    }

    @GetMapping("/current")
    public List<ClientLoanDTO> getClientLoanByClient(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return clientLoanService.getClientLoanDTOsByClientActive(userPrincipal.getUsername());
    }

    @GetMapping("/loans/{code}")
    public List<ClientLoanDTO> getClientLoanByLoan(@PathVariable String code) {
        return clientLoanService.getClientLoanDTOsByLoan(code);
    }

    @Transactional
    @PostMapping("/current")
    public ClientLoanDTO createClientLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return clientLoanService.createClientLoan(loanApplicationDTO, userPrincipal.getUsername());
    }

    @PatchMapping("/current")
    public void payClientLoan(@RequestBody @Valid LoanPaymentDTO loanPaymentDTO, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        clientLoanService.payClientLoan(clientLoanService.getClientLoan(loanPaymentDTO.getCode()), loanPaymentDTO.getAccountNumber(), userPrincipal.getUsername());
    }

}
