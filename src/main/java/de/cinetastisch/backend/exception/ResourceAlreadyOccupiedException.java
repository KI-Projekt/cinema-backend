package de.cinetastisch.backend.exception;

public class ResourceAlreadyOccupiedException extends RuntimeException{

    public ResourceAlreadyOccupiedException(String message){
        super(message);
    }

}
