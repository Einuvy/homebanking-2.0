package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.DTO.response.PersonAuthDTO;
import com.mindhub.homebanking.models.DTO.response.ClientDTO;
import com.mindhub.homebanking.models.DTO.request.ClientPatchDTO;
import com.mindhub.homebanking.models.DTO.request.ClientRegisterDTO;
import com.mindhub.homebanking.models.subModels.Client;

import java.util.List;

public interface ClientService {

    //CREATE
    PersonAuthDTO createClient(ClientRegisterDTO clientRegisterDTO);

    //READ
    List<Client> findAllClients();

    List<Client> findAllClientsByActiveTrue();

    Client findClientByEmail(String email);

    Client findClientById(Long id);

    Boolean existsClientByEmail(String email);

    //READ DTO
    List<ClientDTO> findAllDTOClients();

    List<ClientDTO> findAllClientsDTOByActiveTrue();

    ClientDTO findClientDTOByEmail(String email);

    PersonAuthDTO findClientCurrentByEmail(String email);

    ClientDTO findClientDTOById(Long id);

    //UPDATE
    Client saveClient(Client client);

    ClientDTO updateClient(ClientRegisterDTO clientRegisterDTO, String email);

    ClientDTO patchClient(ClientPatchDTO clientPatchDTO, String email);

    //DELETE
    void deleteClient(Client client);

    void deleteClientById(Long id);

    void deleteClientByEmail(String email);

}
