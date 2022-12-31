package de.cinetastisch.backend.exeption;

public class ResourceAlreadyOccupiedException extends RuntimeException{

    public ResourceAlreadyOccupiedException(String message){
        super(message);
    }

}
