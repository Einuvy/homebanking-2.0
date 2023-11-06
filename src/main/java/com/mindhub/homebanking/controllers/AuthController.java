package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.configuration.UserPrincipal;
import com.mindhub.homebanking.configuration.jwt.JwtProvider;
import com.mindhub.homebanking.models.DTO.request.ClientRegisterDTO;
import com.mindhub.homebanking.models.DTO.request.LoginDTO;
import com.mindhub.homebanking.models.DTO.response.PersonAuthDTO;
import com.mindhub.homebanking.models.superModels.Person;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthController(ClientService clientService,
                          AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider) {
        this.clientService = clientService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }


    @PostMapping("/register")
    public ResponseEntity<PersonAuthDTO> createClient(@RequestBody @Valid ClientRegisterDTO client){
        return new ResponseEntity<>(clientService.createClient(client), CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);

        Person singInPerson = userPrincipal.getPerson();
        PersonAuthDTO personAuthDTO = new PersonAuthDTO(singInPerson);
        personAuthDTO.setToken(jwt);
        return new ResponseEntity<>(personAuthDTO, OK);
    }

}
