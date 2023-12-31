package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.configuration.UserPrincipal;
import com.mindhub.homebanking.models.DTO.response.AccountDTO;
import com.mindhub.homebanking.models.DTO.request.AccountPatchDTO;
import com.mindhub.homebanking.models.DTO.request.AccountRegisterDTO;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final ClientService clientService;

    public AccountController(AccountService accountService,
                             ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
    }



    @GetMapping
    public ResponseEntity<List<AccountDTO>> findAll(){
        return new ResponseEntity<>(accountService.findAllDTOAccounts(), OK);
    }

    @GetMapping("/current")
    public ResponseEntity<List<AccountDTO>> findAllByClient(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(accountService.findAllAccountsDTOByClient(clientService.findClientByEmail(userPrincipal.getUsername())), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id){
        return new ResponseEntity<>(accountService.findAccountDTOById(id), OK);
    }

    @Transactional
    @PostMapping("/current")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountRegisterDTO accountRegisterDTO, @AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(accountService.createAccount(accountRegisterDTO.getAccountType(), userPrincipal.getUsername(), accountRegisterDTO.getCardColor()), CREATED);
    }

    @Transactional
    @DeleteMapping("/current")
    public ResponseEntity<String> deleteAccount(@RequestParam String number, @AuthenticationPrincipal UserPrincipal userPrincipal){
        accountService.deleteAccountByNumber(number, userPrincipal.getUsername());
        return new ResponseEntity<>("Account has been deleted", OK);
    }

    @PatchMapping("/current")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody @Valid AccountPatchDTO accountDTO, @AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>( accountService.updateAccount(accountDTO, userPrincipal.getUsername()), OK);
    }
}
