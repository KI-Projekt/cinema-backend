package de.cinetastisch.backend.handler;

import de.cinetastisch.backend.exception.*;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(value = {
            ResourceAlreadyOccupiedException.class,
            ResourceAlreadyExists.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> handleConflicts(RuntimeException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(true)
        ), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {NoResources.class })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ErrorMessage> handleNoResources(RuntimeException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorMessage(
                HttpStatus.NO_CONTENT.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false)), HttpStatus.NO_CONTENT);
    }

}
