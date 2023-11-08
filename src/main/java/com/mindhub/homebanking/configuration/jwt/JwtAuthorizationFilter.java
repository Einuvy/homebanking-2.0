package com.mindhub.homebanking.configuration.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication;
        try {
            authentication = jwtProvider.getAuthentication(request);
        }catch (MalformedJwtException exception){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Token has been tampered with");
            response.getWriter().flush();
            return;
        }catch (ExpiredJwtException exception){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Token has expired");
            response.getWriter().flush();
            return;
        }

        if (authentication != null && jwtProvider.isTokenValid(request)){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
