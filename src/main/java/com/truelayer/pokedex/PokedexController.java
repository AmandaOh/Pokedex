package com.truelayer.pokedex;

import com.truelayer.pokedex.pokemon.PokemonService;
import com.truelayer.pokedex.pokemon.models.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/pokemon")
@Validated
public class PokedexController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/{name}")
    public Mono<Pokemon> getPokemon(@PathVariable @Pattern(regexp = "^[A-Za-z]+$") String name) {
        return this.pokemonService.getPokemon(name);
    }
}
