package de.cinetastisch.backend.exception;

public class ResourceAlreadyExists extends RuntimeException{
    public ResourceAlreadyExists(String message){
        super(message);
    }
}
