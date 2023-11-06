package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.DTO.request.LoanApplicationDTO;
import com.mindhub.homebanking.models.DTO.response.ClientLoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.subModels.Client;

import java.util.List;

public interface ClientLoanService {
    List<ClientLoan> getClientLoans();

    List<ClientLoan> getClientLoansActive();

    List<ClientLoan> getClientLoanByLoan(String code);

    List<ClientLoan> getClientLoanByClientActive(String email);

    ClientLoan getClientLoan(String code);

    List<ClientLoanDTO> getClientLoanDTOs();

    List<ClientLoanDTO> getClientLoanDTOsActive();

    List<ClientLoanDTO> getClientLoanDTOsByLoan(String code);

    List<ClientLoanDTO> getClientLoanDTOsByClientActive(String email);

    ClientLoanDTO getClientLoanDTO(String code);

    ClientLoanDTO createClientLoan(LoanApplicationDTO loanApplicationDTO);

    ClientLoan saveClientLoan(ClientLoan clientLoan);

    void payClientLoan(ClientLoan clientLoan, String accountNumber);
}
