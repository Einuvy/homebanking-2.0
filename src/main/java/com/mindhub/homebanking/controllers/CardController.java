package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.configuration.UserPrincipal;
import com.mindhub.homebanking.models.DTO.response.CardDTO;
import com.mindhub.homebanking.models.DTO.request.CardRegisterDTO;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;
    private final ClientService clientService;

    public CardController(CardService cardService,
                          ClientService clientService) {
        this.cardService = cardService;
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getCards() {
        return new ResponseEntity<>(cardService.findAllDTOCards(), OK);
    }

    @GetMapping("/current")
    public ResponseEntity<List<CardDTO>> getCardsByClient(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ResponseEntity<>(cardService.findAllCardsDTOByClientEmail(userPrincipal.getUsername()), OK);
    }

    @GetMapping("/number")
    public ResponseEntity<CardDTO> getCard(@RequestParam String number){
        return new ResponseEntity<>(cardService.findCardDTOByNumber(number), OK);
    }

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody @Valid CardRegisterDTO cardRegisterDTO) {
        return new ResponseEntity<>(cardService.createCreditCard(cardRegisterDTO.getCardColor(), cardRegisterDTO.getCreditLimit(), clientService.findClientByEmail(cardRegisterDTO.getEmail())), OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCard(@RequestParam Long id) {
        cardService.deleteCard(cardService.findCardById(id));
        return new ResponseEntity<>("Card has been deleted",OK);
    }

}
