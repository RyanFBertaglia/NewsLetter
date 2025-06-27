package com.newsletter.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.newsletter.model.GoogleLoginRequest;
import com.newsletter.service.GoogleTokenVerifier;
import com.newsletter.service.TokenService;
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
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody GoogleLoginRequest request) {
        boolean isValid = jwtTokenService.validateToken(request.idToken());

        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }
}

