package com.truelayer.pokedex.pokemon.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class PokemonSpeciesResponseDTO {

    String name;

    @JsonProperty("flavor_text_entries")
    List<FlavorTextEntry> flavorTextEntries;

    Habitat habitat;

    @JsonProperty("is_legendary")
    boolean isLegendary;

    @Value
    public static class FlavorTextEntry {

        @JsonProperty("flavor_text")
        String flavorText;

        Language language;
    }

    @Value
    public static class Language {

        String name;
    }

    @Value
    public static class Habitat {

        String name;
    }
}
