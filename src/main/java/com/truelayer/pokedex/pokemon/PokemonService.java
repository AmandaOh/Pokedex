package com.truelayer.pokedex.pokemon;

import com.truelayer.pokedex.pokemon.models.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PokemonService {

    @Autowired
    private PokeApiClient pokeApiClient;

    public Mono<Pokemon> getPokemon(String name) {
        return this.pokeApiClient
                .getSpecies(name)
                .map(Pokemon::mapToPokemon);
    }
}
