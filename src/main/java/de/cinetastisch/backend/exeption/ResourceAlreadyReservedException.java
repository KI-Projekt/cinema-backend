package de.cinetastisch.backend.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ResourceAlreadyReservedException extends RuntimeException{

    public ResourceAlreadyReservedException(String message){
        super(message);
    }

}
