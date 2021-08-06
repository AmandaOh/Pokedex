package com.truelayer.pokedex.pokemon.models;

import lombok.Value;

@Value
public class Pokemon {

    String name;
    String description;
    String habitat;
    boolean isLegendary;

    public static Pokemon mapToPokemon(PokemonSpeciesResponseDTO pokemonSpeciesResponseDTO) {
        String description = pokemonSpeciesResponseDTO.getFlavorTextEntries()
                .stream()
                .filter(entry -> entry
                        .getLanguage()
                        .getName()
                        .equals(Language.ENGLISH.getName()))
                .findFirst()
                .map(PokemonSpeciesResponseDTO.FlavorTextEntry::getFlavorText)
                .orElse("");

        return new Pokemon(
                pokemonSpeciesResponseDTO.getName(),
                description,
                pokemonSpeciesResponseDTO.getHabitat().getName(),
                pokemonSpeciesResponseDTO.isLegendary()
        );
    }
}
