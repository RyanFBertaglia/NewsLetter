package com.newsletter.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.newsletter.model.GoogleLoginRequest;
import com.newsletter.model.User;
import com.newsletter.service.GoogleTokenVerifier;
import com.newsletter.service.TokenService;
import com.newsletter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            String jwt = jwtTokenService.generateTokenFromGooglePayload(payload);

            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar login");
        }
    }
    @PostMapping("/google-register")
    public ResponseEntity<String> googleRegister(@RequestBody GoogleLoginRequest request, String nome) {
        try {
            GoogleIdToken.Payload payload = googleTokenVerifier.verifyToken(request.idToken());
            if (payload == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token do Google inválido");
            }
            userService.createUser(payload.getEmail(), nome);
            String jwt = jwtTokenService.generateTokenFromGooglePayload(payload);

            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar cadastro");
        }
    }
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody GoogleLoginRequest request) {
        boolean isValid = jwtTokenService.validateToken(request.idToken());

        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

