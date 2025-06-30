package com.newsletter.exception;

public class TokenInvalido extends RuntimeException {
    public TokenInvalido(String message) {
        super(message);
    }
    public TokenInvalido() {
        super("Token Inválido");
    }
}
