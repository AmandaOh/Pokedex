package com.truelayer.pokedex.pokemon;

import com.truelayer.pokedex.UrlConfig;
import com.truelayer.pokedex.pokemon.models.PokemonSpeciesResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PokeApiClient {

    private final WebClient webClient;

    public PokeApiClient(UrlConfig urlConfig) {
        this.webClient = WebClient.create(urlConfig.getPokeApi());
    }

    public Mono<PokemonSpeciesResponseDTO> getSpecies(String name) {
        return this.webClient.get()
                .uri("/pokemon-species/{name}", name)
                .retrieve()
                .bodyToMono(PokemonSpeciesResponseDTO.class);
    }
}
