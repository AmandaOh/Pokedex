package com.truelayer.pokedex.pokemon;

import com.truelayer.pokedex.pokemon.models.Pokemon;
import com.truelayer.pokedex.pokemon.models.PokemonSpeciesResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @Mock
    private PokeApiClient pokeApiClient;

    @InjectMocks
    private PokemonService pokemonService;

    private PokemonSpeciesResponseDTO pokemonSpeciesResponseDTO;
    private final String pokemonName = "mewtwo";
    private final String pokemonDescription = "It was created by\na scientist after\nyears of horrific\fgene splicing and\nDNA engineering\nexperiments.";

    @BeforeEach
    void setUp() {
         pokemonSpeciesResponseDTO = new PokemonSpeciesResponseDTO(
                pokemonName,
                List.of(
                        new PokemonSpeciesResponseDTO.FlavorTextEntry(
                                pokemonDescription,
                                new PokemonSpeciesResponseDTO.Language("en"))
                ),
                new PokemonSpeciesResponseDTO.Habitat("rare"),
                true
        );
    }

    @Test
    void getPokemon_maps_pokemon_species_response_to_pokemon() {
        when(pokeApiClient.getSpecies(pokemonName)).thenReturn(
                Mono.just(pokemonSpeciesResponseDTO)
        );

        Pokemon actual = pokemonService.getPokemon(pokemonName).block();

        Pokemon expected = new Pokemon(
                pokemonName,
                pokemonDescription,
                "rare",
                true
        );
        assertEquals(expected, actual);
    }
}