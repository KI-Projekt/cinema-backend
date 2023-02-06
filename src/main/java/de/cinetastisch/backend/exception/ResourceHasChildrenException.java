package de.cinetastisch.backend.exception;

public class ResourceHasChildrenException extends RuntimeException{
    public ResourceHasChildrenException(String message){
        super(message);
    }
}
