package com.newsletter.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.*;

@Service
public class TokenService {
    @Value("{api.security.token.secret}")
    public String secret;

    public String generateTokenFromGooglePayload(GoogleIdToken.Payload payload) {
        return Jwts.builder()
                .setSubject(payload.getSubject()) // ID único do usuário no Google
                .claim("email", payload.getEmail())
                .claim("name", (String) payload.get("name"))
                .setIssuedAt((Date) Date.from(
                        ZonedDateTime.now(ZoneOffset.of("-03:00"))
                                .toInstant()
                ))
                .setExpiration(expirationDate())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date expirationDate() {
        return (Date) Date.from(
                ZonedDateTime.now(ZoneOffset.of("-03:00"))
                        .plusHours(2)
                        .toInstant()
        );
    }
}
