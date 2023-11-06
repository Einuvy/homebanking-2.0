package com.mindhub.homebanking.configuration;

import com.mindhub.homebanking.models.subModels.Client;
import com.mindhub.homebanking.models.superModels.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    @Getter
    private final Long id;
    private final String email;
    @Getter
    transient private String password;
    @Getter
    transient private Person person;
    @Getter
    private final List<GrantedAuthority> authorities;
    public UserPrincipal(Long id, String email, List<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
