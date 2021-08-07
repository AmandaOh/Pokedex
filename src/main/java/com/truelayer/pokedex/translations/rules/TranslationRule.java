package com.truelayer.pokedex.translations.rules;

import com.truelayer.pokedex.pokemon.models.Pokemon;
import reactor.core.publisher.Mono;

public interface TranslationRule {

    boolean isSatisfied(Pokemon pokemon);

    Mono<Pokemon> getTranslation(Pokemon pokemon);
}
