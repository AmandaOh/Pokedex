package com.truelayer.pokedex.translations.rules;

import com.truelayer.pokedex.pokemon.models.Pokemon;
import com.truelayer.pokedex.translations.TranslationService;
import com.truelayer.pokedex.translations.models.TranslationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultTranslationRuleTest {

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private DefaultTranslationRule defaultTranslationRule;

    private Pokemon pokemon;

    @BeforeEach
    void setUp() {
        pokemon = new Pokemon(
                "pikachu",
                "friendly pokemon",
                "cave",
                true
        );
    }

    @Test
    void isSatisfied_returns_true() {
        boolean isSatisfied = defaultTranslationRule.isSatisfied(pokemon);

        assertTrue(isSatisfied);
    }

    @Test
    void getTranslation_returns_shakespeare_translation() {
        String translatedDescription = "gentle pokemon";
        when(translationService.getTranslation(pokemon.getDescription(), TranslationType.SHAKESPEARE)).thenReturn(
                Mono.just(translatedDescription)
        );

        Pokemon actual = defaultTranslationRule.getTranslation(pokemon).block();

        Pokemon expected = new Pokemon(
                pokemon.getName(),
                translatedDescription,
                pokemon.getHabitat(),
                pokemon.isLegendary()
        );
        assertEquals(expected, actual);
    }
}