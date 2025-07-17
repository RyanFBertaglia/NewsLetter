package com.newsletter.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.newsletter.exception.TokenInvalido;
import com.newsletter.model.GoogleLoginRequest;
import com.newsletter.model.User;
import com.newsletter.service.GoogleTokenVerifier;
import com.newsletter.service.TokenService;
import com.newsletter.service.UserService;
import com.nimbusds.jwt.JWT;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;

    @Autowired
    private TokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    @Value("${stripe.price.id}")
    private String priceId;

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

    @PostMapping("renovar")
    public ResponseEntity<String> addVencimento(HttpServletRequest request) {
        User user = jwtTokenService.getUser(request);
        userService.renovarValidade(user);
        return ResponseEntity.ok().body("Renovated successfully");
    }

    @PostMapping("/pagamento")
    public ResponseEntity<?> createCheckoutSession() {
        Stripe.apiKey = stripeApiKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice(priceId)
                                .build()
                )
                .build();

        try {
            Session session = Session.create(params);
            return ResponseEntity.ok(session.getUrl());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar sessão: " + e.getMessage());
        }
    }
}

