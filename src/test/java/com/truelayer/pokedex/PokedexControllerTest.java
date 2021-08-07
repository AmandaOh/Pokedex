package com.truelayer.pokedex;

import com.truelayer.pokedex.pokemon.PokemonService;
import com.truelayer.pokedex.pokemon.models.Pokemon;
import com.truelayer.pokedex.translations.PokemonTranslationRuleEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PokedexController.class)
class PokedexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @MockBean
    private PokemonTranslationRuleEngine pokemonTranslationRuleEngine;

    private final String pokemonName = "pikachu";
    private Pokemon pokemon;

    @BeforeEach
    void setUp() {
        pokemon = new Pokemon(
                pokemonName,
                "friendly pokemon",
                "rare",
                true
        );
        when(pokemonService.getPokemon(pokemonName)).thenReturn(Mono.just(pokemon));
    }

    @Test
    void getPokemon_returns_400_validation_error_when_name_is_not_letters() throws Exception {
        mockMvc.perform(get("/pokemon/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid Request")));
    }

    @Test
    void getPokemon_returns_pokemon_details() throws Exception {
        mockMvc.perform(get("/pokemon/" + pokemonName))
                .andExpect(status().isOk())
                .andExpect(request().asyncResult(pokemon));
    }

    @Test
    void getTranslatedPokemon_returns_400_validation_error_when_name_is_not_letters() throws Exception {
        mockMvc.perform(get("/pokemon/translated/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid Request")));
    }

    @Test
    void getTranslatedPokemon_returns_translated_pokemon() throws Exception {
        Pokemon translatedPokemon = new Pokemon(
                "translated-pikachu",
                "translated-description",
                "translated-habitat",
                true
        );

        when(pokemonTranslationRuleEngine.evaluate(pokemon)).thenReturn(Mono.just(translatedPokemon));

        mockMvc.perform(get("/pokemon/translated/" + pokemonName))
                .andExpect(status().isOk())
                .andExpect(request().asyncResult(translatedPokemon));
    }
}