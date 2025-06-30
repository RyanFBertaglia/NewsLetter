package com.newsletter.exception;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists() {super("Usuário já existente"); }
    public UserAlreadyExists(String message) {super(message); }
}
