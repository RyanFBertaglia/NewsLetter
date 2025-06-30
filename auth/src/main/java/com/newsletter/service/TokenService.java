package com.newsletter.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.newsletter.exception.TokenInvalido;
import com.newsletter.model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class TokenService {

    @Autowired
    UserService userService;

    @Value("${api.security.token.secret}")
    private String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateTokenFromGooglePayload(GoogleIdToken.Payload payload, String nome) {
        return Jwts.builder()
                .setSubject(payload.getSubject())
                .claim("email", payload.getEmail())
                .claim("name", nome)
                .setIssuedAt(currentTime())
                .setExpiration(expirationDate())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public UserDTO validateToken(String token) {
        try {
            Claims user = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
            return this.userService.getUserDatails(user.get("email", String.class));
        } catch (Exception e) {
            throw new TokenInvalido("Token Inválido");
        }
    }

    private Date currentTime() {
        return Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant());
    }

    private Date expirationDate() {
        return Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(2).toInstant());
    }
}
