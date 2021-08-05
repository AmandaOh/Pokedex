package com.truelayer.pokedex.pokemon;

import com.truelayer.pokedex.UrlConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PokemonService {

    private final WebClient webClient;

    public PokemonService(WebClient.Builder builder, UrlConfig urlConfig) {
        this.webClient = builder.baseUrl(urlConfig.getPokeApi()).build();
    }

    public Mono<Pokemon> getPokemon(String name) {
        return this.webClient.get()
                .uri("/pokemon-species/{name}", name)
                .retrieve()
                .bodyToMono(Pokemon.class);
    }
}
