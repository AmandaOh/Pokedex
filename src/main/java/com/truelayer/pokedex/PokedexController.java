package com.truelayer.pokedex;

import com.truelayer.pokedex.pokemon.models.Pokemon;
import com.truelayer.pokedex.pokemon.models.PokemonSpeciesResponseDTO;
import com.truelayer.pokedex.pokemon.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pokemon")
public class PokedexController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/{name}")
    public Mono<Pokemon> getPokemon(@PathVariable String name) {
        return this.pokemonService.getPokemon(name);
    }
}
