package com.truelayer.pokedex.translations;

import com.truelayer.pokedex.pokemon.models.Pokemon;
import com.truelayer.pokedex.translations.rules.DefaultTranslationRule;
import com.truelayer.pokedex.translations.rules.YodaTranslationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokemonTranslationRuleEngineTest {

    @Mock
    private YodaTranslationRule yodaTranslationRule;

    @Mock
    private DefaultTranslationRule defaultTranslationRule;

    @InjectMocks
    private PokemonTranslationRuleEngine pokemonTranslationRuleEngine;

    private Pokemon pokemon;
    private Pokemon translatedPokemon;

    @BeforeEach
    void setUp() {
        pokemon = new Pokemon(
                "pikachu",
                "friendly pokemon",
                "rare",
                true
        );
        translatedPokemon = new Pokemon(
                "translated-pokemon",
                "translated description",
                "translated habitat",
                true
        );
    }

    @Test
    void evaluate_should_return_yoda_translation_if_conditions_are_satisfied() {
        when(yodaTranslationRule.isSatisfied(pokemon)).thenReturn(true);
        when(yodaTranslationRule.getTranslation(pokemon)).thenReturn(Mono.just(translatedPokemon));

        Pokemon actual = pokemonTranslationRuleEngine.evaluate(pokemon).block();

        assertEquals(translatedPokemon, actual);
    }

    @Test
    void evaluate_should_return_default_translation_if_no_conditions_are_satisfied() {
        when(yodaTranslationRule.isSatisfied(pokemon)).thenReturn(false);
        when(defaultTranslationRule.getTranslation(pokemon)).thenReturn(Mono.just(translatedPokemon));

        Pokemon actual = pokemonTranslationRuleEngine.evaluate(pokemon).block();

        assertEquals(translatedPokemon, actual);
    }
}