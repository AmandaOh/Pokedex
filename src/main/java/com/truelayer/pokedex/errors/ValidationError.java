package com.truelayer.pokedex.errors;

import lombok.Value;

import java.util.List;

@Value
public class ValidationError {

    String message;
    List<String> errors;
}
