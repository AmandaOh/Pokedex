package com.truelayer.pokedex.translations;

import com.truelayer.pokedex.pokemon.models.Pokemon;
import com.truelayer.pokedex.translations.rules.DefaultTranslationRule;
import com.truelayer.pokedex.translations.rules.YodaTranslationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Component
public class PokemonTranslationRuleEngine {

    @Autowired
    private YodaTranslationRule yodaTranslationRule;

    @Autowired
    private DefaultTranslationRule defaultTranslationRule;

    public Mono<Pokemon> evaluate(Pokemon pokemon) {
        return Stream.of(yodaTranslationRule)
                .filter(rule -> rule.isSatisfied(pokemon))
                .findFirst()
                .map(rule -> rule.getTranslation(pokemon))
                .orElse(defaultTranslationRule.getTranslation(pokemon));
    }
}
