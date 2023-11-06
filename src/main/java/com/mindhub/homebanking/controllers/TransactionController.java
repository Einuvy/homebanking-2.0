package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.DTO.request.TransactionCreateDTO;
import com.mindhub.homebanking.models.DTO.response.TransactionDTO;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/account")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccount(@RequestParam String number) {
        return new ResponseEntity<>(transactionService.findAllDTOByAccountNumber(number), OK);
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionCreateDTO transactionCreateDTO) {
        if (transactionCreateDTO.getAmount() <= 0) throw new ResponseStatusException(BAD_REQUEST, "Amount must be greater than 0");
        return new ResponseEntity<>(transactionService.createTransaction(transactionCreateDTO), OK);
    }

}
