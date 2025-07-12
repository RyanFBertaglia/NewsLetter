package com.newsletter.exception;

public class AlreadyAnswered extends RuntimeException {
    public AlreadyAnswered(String message) {
        super(message);
    }
    public AlreadyAnswered() {super("You have already answered this form"); }
}
