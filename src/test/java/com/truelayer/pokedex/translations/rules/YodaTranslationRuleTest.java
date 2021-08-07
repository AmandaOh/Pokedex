package com.truelayer.pokedex.translations.rules;

import com.truelayer.pokedex.pokemon.models.Pokemon;
import com.truelayer.pokedex.translations.TranslationService;
import com.truelayer.pokedex.translations.models.TranslationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YodaTranslationRuleTest {

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private YodaTranslationRule yodaTranslationRule;

    @Test
    void isSatisfied_returns_true_if_pokemon_habitat_is_cave() {
        Pokemon pokemon = new Pokemon(
                "pikachu",
                "friendly pokemon",
                "cave",
                true
        );

        boolean isSatisfied = yodaTranslationRule.isSatisfied(pokemon);

        assertTrue(isSatisfied);
    }

    @Test
    void isSatisfied_returns_true_if_pokemon_is_legendary() {
        Pokemon pokemon = new Pokemon(
                "pikachu",
                "friendly pokemon",
                "rare",
                true
        );

        boolean isSatisfied = yodaTranslationRule.isSatisfied(pokemon);

        assertTrue(isSatisfied);
    }

    @Test
    void isSatisfied_returns_false_if_pokemon_is_not_legendary_and_habitat_is_not_cave() {
        Pokemon pokemon = new Pokemon(
                "pikachu",
                "friendly pokemon",
                "rare",
                false
        );

        boolean isSatisfied = yodaTranslationRule.isSatisfied(pokemon);

        assertFalse(isSatisfied);
    }

    @Test
    void getTranslation_returns_yoda_translation() {
        Pokemon pokemon = new Pokemon(
                "pikachu",
                "friendly pokemon",
                "rare",
                true
        );
        String translatedDescription = "friendly, it was.";
        when(translationService.getTranslation(pokemon.getDescription(), TranslationType.YODA)).thenReturn(
                Mono.just(translatedDescription)
        );

        Pokemon actual = yodaTranslationRule.getTranslation(pokemon).block();

        Pokemon expected = new Pokemon(
                pokemon.getName(),
                translatedDescription,
                pokemon.getHabitat(),
                pokemon.isLegendary()
        );
        assertEquals(expected, actual);
    }
}