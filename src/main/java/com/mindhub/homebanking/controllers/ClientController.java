package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.configuration.UserPrincipal;
import com.mindhub.homebanking.models.DTO.response.PersonAuthDTO;
import com.mindhub.homebanking.models.DTO.response.ClientDTO;
import com.mindhub.homebanking.models.DTO.request.ClientPatchDTO;
import com.mindhub.homebanking.models.DTO.request.ClientRegisterDTO;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }



    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(){
        return new ResponseEntity<>(clientService.findAllClientsDTOByActiveTrue(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id){
        return new ResponseEntity<>(clientService.findClientDTOById(id), OK);
    }

    @GetMapping("/current")
    public ResponseEntity<PersonAuthDTO> getClient(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(clientService.findClientCurrentByEmail(userPrincipal.getUsername()), OK);
    }

    @PutMapping("/current")
    public ResponseEntity<ClientDTO> updateClient(@RequestBody @Valid ClientRegisterDTO clientRegisterDTO, @AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(clientService.updateClient(clientRegisterDTO, userPrincipal.getUsername()), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDTO> patchClient(@RequestBody ClientPatchDTO clientPatchDTO, @AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(clientService.patchClient(clientPatchDTO, userPrincipal.getUsername()), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@AuthenticationPrincipal UserPrincipal userPrincipal){
        clientService.deleteClientByEmail(userPrincipal.getUsername());
        return new ResponseEntity<>("Client has been deleted", OK);
    }

}
