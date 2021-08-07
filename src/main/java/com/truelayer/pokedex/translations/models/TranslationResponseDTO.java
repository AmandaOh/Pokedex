package com.truelayer.pokedex.translations.models;

import lombok.Value;

@Value
public class TranslationResponseDTO {

    Content contents;

    @Value
    public static class Content {

        String translated;
    }
}
