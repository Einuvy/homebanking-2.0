package com.mindhub.homebanking.configuration;

import com.mindhub.homebanking.configuration.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class Authentication {

    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public Authentication(CustomUserDetailsServiceImpl customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = auth.build();


        http.csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers(POST, "/api/auth/login", "/api/auth/register").permitAll()
                .antMatchers(GET, "/api/clients/find-one", "/api/accounts/{id}", "/api/cards/{number}", "/api/client-loans/{number}", "/api/transaction/account").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current", "/api/accounts/current", "/api/cards/current", "/api/client-loans/current", "/api/transactions/current").hasAuthority("CLIENT")
                .antMatchers(GET, "/api/clients", "/api/accounts").hasAuthority("ADMIN")
                .antMatchers( "/api/loans/*").hasAuthority("ADMIN")
                .anyRequest().denyAll()


                .and().authenticationManager(authenticationManager)
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable();


        return http.build();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }
}
