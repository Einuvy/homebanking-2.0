package com.mindhub.homebanking.configuration;

import com.mindhub.homebanking.models.subModels.Client;
import com.mindhub.homebanking.models.superModels.Person;
import com.mindhub.homebanking.repositories.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;

    public CustomUserDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEmailAndActiveTrue(email).orElseThrow(
                () -> new UsernameNotFoundException("Unknown user: " + email));

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(person.getRole().toString());
        return new UserPrincipal(
                person.getId(),
                person.getEmail(),
                person.getPassword(),
                person, authorities);
    }
}
