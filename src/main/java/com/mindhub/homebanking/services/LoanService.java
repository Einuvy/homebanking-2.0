package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.DTO.request.LoanCreationDTO;
import com.mindhub.homebanking.models.DTO.response.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> getAll();

    List<Loan> getAllLoansActive();

    Loan getLoan(String code);

    List<LoanDTO> getLoansDTO();

    List<LoanDTO> getLoansActiveDTO();

    LoanDTO getLoanDTO(String code);

    Loan saveLoan(Loan loan);

    void deleteLoan(Loan loan);

    void deleteLoan(String code);

    LoanDTO createLoanDTO(LoanCreationDTO loanCreationDTO);

    LoanDTO updateLoanDTO(String code, LoanCreationDTO loanCreationDTO);

    LoanDTO activateLoan(String code);

    LoanDTO patchLoan(String code, LoanCreationDTO loanCreationDTO);
}
