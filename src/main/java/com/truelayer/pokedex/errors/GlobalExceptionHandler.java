package com.truelayer.pokedex.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ WebClientResponseException.NotFound.class })
    public final ResponseEntity<ApiError> handleNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ApiError("Pokemon does not exist."), HttpStatus.NOT_FOUND);
    }
}
