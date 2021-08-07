package com.truelayer.pokedex.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ WebClientResponseException.NotFound.class })
    public final ResponseEntity<ApiError> handleNotFoundException(WebRequest webRequest) {
        return new ResponseEntity<>(new ApiError("Pokemon does not exist"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ValidationError> handleValidationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                new ValidationError("Invalid Request", errors),
                HttpStatus.BAD_REQUEST
        );
    }
}
