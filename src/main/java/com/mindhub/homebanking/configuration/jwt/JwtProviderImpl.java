package com.mindhub.homebanking.configuration.jwt;

import com.mindhub.homebanking.configuration.UserPrincipal;
import com.mindhub.homebanking.models.DTO.response.PersonAuthDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;

import static com.mindhub.homebanking.utils.SecurityUtils.extractAuthTokenFromRequest;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

@Component
public class JwtProviderImpl implements JwtProvider{
    @Value("${app.jwtSecret}")
    private String JWT_SECRET;

    @Value("${app.jwtExpirationMs}")
    private Long JWT_EXPIRATION_IN_MS;

    @Override
    public String generateToken(UserPrincipal auth){
        String authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(joining(","));

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(UTF_8));


        return Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles", authorities)
                .claim("userId", auth.getId())
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION_IN_MS))
                .signWith(key, HS512)
                .compact();
    }

    @Override
    public String generateToken(PersonAuthDTO client){
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(UTF_8));


        return Jwts.builder()
                .setSubject(client.getEmail())
                .claim("roles", client.getRole())
                .claim("userId", client.getId())
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION_IN_MS))
                .signWith(key, HS512)
                .compact();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request){
        Claims claims = extractClaims(request);
        if(claims == null){
            return null;
        }
        String username = claims.getSubject();
        Long clientId = claims.get("userId", Long.class);

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(claims.get("roles").toString().split(","));

        UserDetails userDetails = new UserPrincipal(clientId, username, authorities);

        if (username==null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request){
        Claims claims = extractClaims(request);

        if (claims==null){
            return false;
        }

        return !claims.getExpiration().before(new Date());
    }

    private Claims extractClaims(HttpServletRequest request){
        String token = extractAuthTokenFromRequest(request);

        if (token == null){
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
