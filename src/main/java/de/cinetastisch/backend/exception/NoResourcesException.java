package de.cinetastisch.backend.exception;

public class NoResourcesException extends RuntimeException {
    public NoResourcesException(String message) {
        super(message);
    }
}
