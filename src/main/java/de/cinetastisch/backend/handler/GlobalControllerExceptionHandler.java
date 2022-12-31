package de.cinetastisch.backend.handler;

import de.cinetastisch.backend.exeption.ErrorMessage;
import de.cinetastisch.backend.exeption.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.exeption.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Hidden
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFound(RuntimeException ex, WebRequest webRequest){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false)
        );
    }

    @ExceptionHandler(ResourceAlreadyOccupiedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleResourceAlreadyOccupied(RuntimeException ex, WebRequest webRequest){
        return new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false)
        );
    }
}
