package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.models.DTO.response.CardDTO;
import com.mindhub.homebanking.models.ENUM.CardColor;
import com.mindhub.homebanking.models.subModels.Client;
import com.mindhub.homebanking.models.subModels.CreditCard;
import com.mindhub.homebanking.models.subModels.DebitCard;
import com.mindhub.homebanking.models.superModels.Card;
import com.mindhub.homebanking.repositories.CreditCardRepository;
import com.mindhub.homebanking.repositories.DebitCardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.CardsUtils.getRandomCVV;
import static com.mindhub.homebanking.utils.CardsUtils.getRandomCardNumber;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CardServiceImpl implements CardService {

    private final CreditCardRepository creditCardRepository;
    private final DebitCardRepository debitCardRepository;
    private final ClientService clientService;


    public CardServiceImpl(CreditCardRepository creditCardRepository,
                           DebitCardRepository debitCardRepository,
                           ClientService clientService) {
        this.creditCardRepository = creditCardRepository;
        this.debitCardRepository = debitCardRepository;
        this.clientService = clientService;
    }


    //CREATE
    @Override
    public CardDTO createCreditCard(CardColor cardColor, Double creditLimit, Client client) {

        CreditCard creditCard = new CreditCard(cardColor,getRandomCardNumber(), getRandomCVV(), creditLimit);
        client.addCreditCard(creditCard);
        clientService.saveClient(client);
        return new CardDTO(saveCreditCard(creditCard));
    }

    @Override
    public DebitCard createDebitCard(CardColor cardColor) {
        DebitCard debitCard = new DebitCard(cardColor,getRandomCardNumber(), getRandomCVV());
        return saveDebitCard(debitCard);
    }

    //READ
    @Override
    public List<Card> findAllCards() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(creditCardRepository.findByActiveTrue());
        cards.addAll(debitCardRepository.findByActiveTrue());
        return cards;
    }
    @Override
    public List<Card> findAllCardsByClientEmail(String email) {
       if (clientService.existsClientByEmail(email)) {
           List<Card> cards = new ArrayList<>();
           cards.addAll(creditCardRepository.findByClientEmailAndActiveTrue(email));
           cards.addAll(debitCardRepository.findByAccount_Client_EmailAndActiveTrue(email));
           return cards;
       }
        throw new ResponseStatusException(NOT_FOUND, "Client not found");
    }

    @Override
    public Card findCardById(Long id) {
        if (creditCardRepository.existsById(id)) return creditCardRepository.findById(id).orElse(null);
        return debitCardRepository.findById(id).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Card not found"));
    }

    @Override
    public Card findCardByNumber(String number) {
        if (creditCardRepository.existsByNumberAndActiveTrue(number))return creditCardRepository.findByNumberAndActiveTrue(number).orElse(null);
        return debitCardRepository.findByNumberAndActiveTrue(number).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Card not found"));
    }

    //READ DTO
    @Override
    public List<CardDTO> findAllDTOCards() {
        return findAllCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> findAllCardsDTOByClientEmail(String email) {
        return findAllCardsByClientEmail(email).stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @Override
    public CardDTO findCardDTOById(Long id) {
        return new CardDTO(findCardById(id));
    }

    @Override
    public CardDTO findCardDTOByNumber(String number) {
        return new CardDTO(findCardByNumber(number));
    }

    //UPDATE
    @Override
    public DebitCard saveDebitCard(DebitCard debitCard) {
        return debitCardRepository.save(debitCard);
    }
    @Override
    public CreditCard saveCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    //DELETE
    @Override
    public void deleteCard(Card card) {
        if (card instanceof DebitCard) debitCardRepository.delete((DebitCard) card);
        if (card instanceof CreditCard) creditCardRepository.delete((CreditCard) card);
    }

}
