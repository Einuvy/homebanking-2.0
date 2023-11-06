package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.models.DTO.request.LoanCreationDTO;
import com.mindhub.homebanking.models.DTO.response.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.LoanUtils.generateLoanCode;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public List<Loan> getAll(){
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> getAllLoansActive() {
        return loanRepository.findByActiveTrue();
    }

    @Override
    public Loan getLoan(String code) {
        return loanRepository.findByCode(code).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Loan not found"));
    }

    @Override
    public List<LoanDTO> getLoansDTO(){
        return getAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<LoanDTO> getLoansActiveDTO(){
        return getAllLoansActive().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public LoanDTO getLoanDTO(String code){
        return new LoanDTO(getLoan(code));
    }

    @Override
    public Loan saveLoan(Loan loan){
        return loanRepository.save(loan);
    }

    @Override
    public void deleteLoan(Loan loan){
        loanRepository.delete(loan);
    }

    @Override
    public void deleteLoan(String code){
        loanRepository.delete(getLoan(code));
    }

    @Override
    public LoanDTO createLoanDTO(LoanCreationDTO loanCreationDTO){
        String code;
        do {
            code = generateLoanCode();
        }while (loanRepository.existsByCode(code));
        Loan loan = new Loan(code,
                loanCreationDTO.getName(),
                loanCreationDTO.getDescription(),
                loanCreationDTO.getInterestRate(),
                loanCreationDTO.getMaxAmount(),
                loanCreationDTO.getMinAmount(),
                loanCreationDTO.getPayments());
        return new LoanDTO(saveLoan(loan));
    }

    @Override
    public LoanDTO updateLoanDTO(String code, LoanCreationDTO loanCreationDTO){
        Loan loan = getLoan(code);
        loan.setName(loanCreationDTO.getName());
        loan.setDescription(loanCreationDTO.getDescription());
        loan.setInterestRate(loanCreationDTO.getInterestRate());
        loan.setMaxAmount(loanCreationDTO.getMaxAmount());
        loan.setMinAmount(loanCreationDTO.getMinAmount());
        loan.setPayments(loanCreationDTO.getPayments());
        return new LoanDTO(saveLoan(loan));
    }

    @Override
    public LoanDTO activateLoan(String code){
        Loan loan = getLoan(code);
        loan.setActive(true);
        return new LoanDTO(saveLoan(loan));
    }

    @Override
    public LoanDTO patchLoan(String code, LoanCreationDTO loanCreationDTO){
        Loan loan = getLoan(code);
        if (loanCreationDTO.getName() != null) loan.setName(loanCreationDTO.getName());
        if (loanCreationDTO.getDescription() != null) loan.setDescription(loanCreationDTO.getDescription());
        if (loanCreationDTO.getInterestRate() != null) loan.setInterestRate(loanCreationDTO.getInterestRate());
        if (loanCreationDTO.getMaxAmount() != null) loan.setMaxAmount(loanCreationDTO.getMaxAmount());
        if (loanCreationDTO.getMinAmount() != null) loan.setMinAmount(loanCreationDTO.getMinAmount());
        if (loanCreationDTO.getPayments() != null) loan.setPayments(loanCreationDTO.getPayments());
        return new LoanDTO(saveLoan(loan));
    }

}
