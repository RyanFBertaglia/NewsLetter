package com.newsletter.exception;

public class VersionNotFound extends RuntimeException {
    public VersionNotFound(String message) {
        super(message);
    }
    public VersionNotFound() {
        super("Version not found");
    }
}
