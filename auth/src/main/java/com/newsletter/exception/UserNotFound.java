package com.newsletter.exception;

public class UserNotFound extends RuntimeException {
  public UserNotFound() {super("Email ou senha incorretos"); }
  public UserNotFound(String message) {super(message); }
}
