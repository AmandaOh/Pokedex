package com.truelayer.pokedex.translations.rules;

import com.truelayer.pokedex.pokemon.models.Pokemon;
import com.truelayer.pokedex.translations.TranslationService;
import com.truelayer.pokedex.translations.models.TranslationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class YodaTranslationRule implements TranslationRule {

    @Autowired
    private TranslationService translationService;

    @Override
    public boolean isSatisfied(Pokemon pokemon) {
        String CAVE_HABITAT = "cave";
        return pokemon.getHabitat().equals(CAVE_HABITAT) || pokemon.isLegendary();
    }

    @Override
    public Mono<Pokemon> getTranslation(Pokemon pokemon) {
        return translationService
                .getTranslation(pokemon.getDescription(), TranslationType.YODA)
                .map(description -> new Pokemon(
                        pokemon.getName(),
                        description,
                        pokemon.getHabitat(),
                        pokemon.isLegendary()
                ));
    }
}
