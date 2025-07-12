package com.newsletter.exception;

public class UpdateFailedException extends RuntimeException {
    public UpdateFailedException(String message) {
        super(message);
    }
    public UpdateFailedException() {
        super("Error while updating database");
    }
}
