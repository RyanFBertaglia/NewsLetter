package com.newsletter.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.newsletter.exception.TokenInvalido;
import com.newsletter.model.GoogleLoginRequest;
import com.newsletter.service.GoogleTokenVerifier;
import com.newsletter.service.TokenService;
import com.newsletter.service.UserService;
import com.nimbusds.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;

    @Autowired
    private TokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/google-login")
    public ResponseEntity<String> googleLogin(@RequestBody GoogleLoginRequest request) {
        try {
            GoogleIdToken.Payload payload = googleTokenVerifier.verifyToken(request.idToken());
            if (payload == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token do Google inválido");
            }
            String jwt = jwtTokenService.generateTokenFromGooglePayload(payload, request.nome());

            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar login");
        }
    }
    @PostMapping("/google-register")
    public ResponseEntity<String> googleRegister(@RequestBody GoogleLoginRequest request) throws Exception {
        GoogleIdToken.Payload payload = googleTokenVerifier.verifyToken(request.idToken());
        userService.createUser(payload.getEmail(), request.nome());
        String jwt = jwtTokenService.generateTokenFromGooglePayload(payload, request.nome());

        return ResponseEntity.status(200).body(jwt);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new TokenInvalido("Token mal formado");
        }
        String token = authHeader.substring(7);
        return ResponseEntity.ok().body(jwtTokenService.validateToken(token));
    }


    @PostMapping("/vencimento")
    public ResponseEntity<?> validateMensalidade(@RequestBody JWT request) {
        return ResponseEntity.ok().build();
    }
}

