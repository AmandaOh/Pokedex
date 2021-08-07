package com.truelayer.pokedex.translations;

import com.truelayer.pokedex.UrlConfig;
import com.truelayer.pokedex.translations.models.TranslationRequestDTO;
import com.truelayer.pokedex.translations.models.TranslationResponseDTO;
import com.truelayer.pokedex.translations.models.TranslationType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TranslationApiClient {

    private final WebClient webClient;

    public TranslationApiClient(UrlConfig urlConfig) {
        this.webClient = WebClient.create(urlConfig.getTranslationsApi());
    }

    public Mono<TranslationResponseDTO> translate(String text, TranslationType type) {
        return webClient.post()
                .uri("/{type}", type.getType())
                .body(Mono.just(new TranslationRequestDTO(text)), TranslationRequestDTO.class)
                .retrieve()
                .bodyToMono(TranslationResponseDTO.class);
    }
}
