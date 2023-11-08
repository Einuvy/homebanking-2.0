package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.configuration.UserPrincipal;
import com.mindhub.homebanking.models.DTO.response.ClientBasicDTO;
import com.mindhub.homebanking.models.DTO.response.PersonAuthDTO;
import com.mindhub.homebanking.models.DTO.response.ClientDTO;
import com.mindhub.homebanking.models.DTO.request.ClientPatchDTO;
import com.mindhub.homebanking.models.DTO.request.ClientRegisterDTO;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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

    @GetMapping ("/find-one")
    public ResponseEntity<ClientBasicDTO> getClient(@RequestParam String email){
        return new ResponseEntity<>(clientService.findClientBasicDTOByEmail(email), OK);
    }

    @GetMapping("/current")
    public ResponseEntity<ClientDTO> getClient(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(clientService.findClientCurrentByEmail(userPrincipal.getUsername()), OK);
    }

    @PutMapping("/current") //Deberia cambiar el DTO para no traer datos innecesarios
    public ResponseEntity<ClientDTO> updateClient(@RequestBody @Valid ClientPatchDTO clientPatchDTO, @AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(clientService.updateClient(clientPatchDTO, userPrincipal.getUsername()), OK);
    }

    @PatchMapping("/current")  //Deberia cambiar el DTO para no traer datos innecesarios
    public ResponseEntity<ClientDTO> patchClient(@RequestBody ClientPatchDTO clientPatchDTO, @AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(clientService.patchClient(clientPatchDTO, userPrincipal.getUsername()), OK);
    }

    @Transactional
    @DeleteMapping("/current")
    public ResponseEntity<String> deleteClient(@AuthenticationPrincipal UserPrincipal userPrincipal){
        clientService.deleteClientByEmail(userPrincipal.getUsername());
        return new ResponseEntity<>("Client has been deleted", OK);
    }

}
