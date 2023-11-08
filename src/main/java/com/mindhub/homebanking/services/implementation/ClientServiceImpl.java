package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.configuration.jwt.JwtProvider;
import com.mindhub.homebanking.models.DTO.response.ClientBasicDTO;
import com.mindhub.homebanking.models.DTO.response.PersonAuthDTO;
import com.mindhub.homebanking.models.DTO.response.ClientDTO;
import com.mindhub.homebanking.models.DTO.request.ClientPatchDTO;
import com.mindhub.homebanking.models.DTO.request.ClientRegisterDTO;
import com.mindhub.homebanking.models.subModels.CheckingAccount;
import com.mindhub.homebanking.models.subModels.Client;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper mapper;
    private final CreditCardRepository creditCardRepository;
    private final CheckingAccountRepository checkingAccountRepository;
    private final SavingAccountRepository savingAccountRepository;
    private final DebitCardRepository debitCardRepository;
    private final PersonRepository personRepository;

    private final JwtProvider jwtProvider;

    public ClientServiceImpl(ClientRepository clientRepository,
                             ModelMapper mapper,
                             CreditCardRepository creditCardRepository,
                             CheckingAccountRepository checkingAccountRepository,
                             SavingAccountRepository savingAccountRepository,
                             DebitCardRepository debitCardRepository,
                             PersonRepository personRepository,
                             JwtProvider jwtProvider) {
        this.clientRepository = clientRepository;
        this.mapper = mapper;
        this.creditCardRepository = creditCardRepository;
        this.checkingAccountRepository = checkingAccountRepository;
        this.savingAccountRepository = savingAccountRepository;
        this.debitCardRepository = debitCardRepository;
        this.personRepository = personRepository;
        this.jwtProvider = jwtProvider;
    }

    //CREATE
    @Override
    public PersonAuthDTO createClient(ClientRegisterDTO clientRegisterDTO) {
        if (personRepository.existsByEmailIgnoreCaseAndActiveTrue(clientRegisterDTO.getEmail())) {
            throw new ResponseStatusException(BAD_REQUEST, "Email already exists");
        }
        if (personRepository.existsByDniAndActiveTrue(clientRegisterDTO.getDni())) {
            throw new ResponseStatusException(BAD_REQUEST, "DNI already exists");
        }
        Client client = mapper.map(clientRegisterDTO, Client.class);

        PersonAuthDTO personAuthDTO = new PersonAuthDTO(clientRepository.save(client));
        String token = jwtProvider.generateToken(personAuthDTO);
        personAuthDTO.setToken(token);
        return personAuthDTO;
    }

    //READ
    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> findAllClientsByActiveTrue() {
        return clientRepository.findByActiveTrue();
    }

    @Override
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmailAndActiveTrue(email).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Client not found"));
    }

    @Override
    public Client findClientById(Long id) {
        return clientRepository.findByIdAndActiveTrue(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Client not found"));
    }

    @Override
    public Boolean existsClientByEmail(String email) {
        return clientRepository.existsByEmailIgnoreCaseAndActiveTrue(email);
    }

    //READ DTO
    @Override
    public List<ClientDTO> findAllDTOClients() {
        return findAllClients().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ClientDTO> findAllClientsDTOByActiveTrue() {
        return findAllClientsByActiveTrue().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientDTO findClientDTOByEmail(String email) {
        return new ClientDTO(findClientByEmail(email));
    }
    @Override
    public ClientBasicDTO findClientBasicDTOByEmail(String email) {
        return new ClientBasicDTO(findClientByEmail(email));
    }

    @Override
    public ClientDTO findClientCurrentByEmail(String email) {
        return new ClientDTO(findClientByEmail(email));
    }


    @Override
    public ClientDTO findClientDTOById(Long id) {
        return new ClientDTO(findClientById(id));
    }

    //UPDATE
    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public ClientDTO updateClient(ClientPatchDTO clientPatchDTO, String email) {
        Client clientToUpdate = findClientByEmail(email);

        if (clientRepository.existsByDniAndActiveTrue(clientPatchDTO.getDni())) {
            throw new ResponseStatusException(BAD_REQUEST, "DNI already exists");
        }

        clientToUpdate.setFirstName(clientPatchDTO.getFirstName());
        clientToUpdate.setLastName(clientPatchDTO.getLastName());
        clientToUpdate.setPassword(clientPatchDTO.getPassword());
        clientToUpdate.setDni(clientPatchDTO.getDni());
        clientToUpdate.setBirthDate(clientPatchDTO.getBirthDate());

        saveClient(clientToUpdate);

        return new ClientDTO(clientToUpdate);
    }

    @Override
    public ClientDTO patchClient(ClientPatchDTO clientPatchDTO, String email) {

        Client clientToUpdate = findClientByEmail(email);

        if (clientRepository.existsByDniAndActiveTrue(clientPatchDTO.getDni())) {
            throw new ResponseStatusException(BAD_REQUEST, "DNI already exists");
        }

        if (clientPatchDTO.getFirstName() != null) {
            clientToUpdate.setFirstName(clientPatchDTO.getFirstName());
        }
        if (clientPatchDTO.getLastName() != null) {
            clientToUpdate.setLastName(clientPatchDTO.getLastName());
        }
        if (clientPatchDTO.getPassword() != null) {
            clientToUpdate.setPassword(clientPatchDTO.getPassword());
        }
        if (clientPatchDTO.getDni() != null) {
            clientToUpdate.setDni(clientPatchDTO.getDni());
        }
        if (clientPatchDTO.getBirthDate() != null) {
            clientToUpdate.setBirthDate(clientPatchDTO.getBirthDate());
        }

        return new ClientDTO(saveClient(clientToUpdate));
    }

    //DELETE
    @Override
    public void deleteClient(Client client) {
        client.setActive(false);
        saveClient(client);
    }

    @Override
    public void deleteClientById(Long id) {
        Client client = findClientById(id);
        client.setActive(false);
        client.getAccounts().forEach(account -> {
            account.setActive(false);
            if (account instanceof CheckingAccount){
                debitCardRepository.deleteByAccount(((CheckingAccount) account));
            }
        });
        /*client.getCheckingAccounts().forEach(checkingAccount -> {
            checkingAccount.getDebitCard().setActive(false);
            debitCardRepository.save(checkingAccount.getDebitCard());
            checkingAccount.setActive(false);
            checkingAccountRepository.save(checkingAccount);
        });
        client.getSavingAccounts().forEach(savingAccount -> {
            savingAccount.setActive(false);
            savingAccountRepository.save(savingAccount);
        });*/
        creditCardRepository.deleteAll(client.getCreditCards());

        saveClient(client);
    }

    @Override
    public void deleteClientByEmail(String email) {
        Client clientToDelete = findClientByEmail(email);
        clientToDelete.setActive(false);
        clientToDelete.getAccounts().forEach(account -> {
            account.setActive(false);
            if (account instanceof CheckingAccount){
                debitCardRepository.deleteByAccount(((CheckingAccount) account));
            }
        });

        /*clientToDelete.getCheckingAccounts().forEach(checkingAccount -> {
            checkingAccount.getDebitCard().setActive(false);
            debitCardRepository.save(checkingAccount.getDebitCard());
            checkingAccount.setActive(false);
            checkingAccountRepository.save(checkingAccount);
        });
        clientToDelete.getSavingAccounts().forEach(savingAccount -> {
            savingAccount.setActive(false);
            savingAccountRepository.save(savingAccount);
        });*/
        clientRepository.save(clientToDelete);
        creditCardRepository.deleteAll(clientToDelete.getCreditCards());
    }

    @Override
    public Boolean existsByEmailAndActiveTrueAndAccount(String email, String number) {
        return clientRepository.existsByEmailAndActiveTrueAndAccounts_Number(email, number);
    }


}
