package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.DTO.request.LoanCreationDTO;
import com.mindhub.homebanking.models.DTO.response.LoanDTO;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;


    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getLoans(){
        return new ResponseEntity<>(loanService.getLoansActiveDTO(), OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<LoanDTO> getLoan(@PathVariable String code){
        return new ResponseEntity<>(loanService.getLoanDTO(code), OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody @Valid LoanCreationDTO loanCreationDTO){
        return new ResponseEntity<>(loanService.createLoanDTO(loanCreationDTO), OK);
    }

    @PutMapping("/{code}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable String code, @RequestBody @Valid LoanCreationDTO loanCreationDTO){
        return new ResponseEntity<>(loanService.updateLoanDTO(code, loanCreationDTO), OK);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<LoanDTO> patchLoan(@PathVariable String code, @RequestBody LoanCreationDTO loanCreationDTO){
        return new ResponseEntity<>(loanService.patchLoan(code, loanCreationDTO), OK);
    }

    @Transactional
    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteLoan(@PathVariable String code) {
        loanService.deleteLoan(code);
        return new ResponseEntity<>("Loan has been deleted", OK);
    }

    @PatchMapping("/{code}/activate")
    public ResponseEntity<LoanDTO> activateLoan(@PathVariable String code){
        return new ResponseEntity<>(loanService.activateLoan(code), OK);
    }

}
