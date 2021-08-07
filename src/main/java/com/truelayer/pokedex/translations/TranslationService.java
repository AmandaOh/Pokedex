package com.truelayer.pokedex.translations;

import com.truelayer.pokedex.translations.models.TranslationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TranslationService {

    @Autowired
    private TranslationApiClient translationApiClient;

    public Mono<String> getTranslation(String text, TranslationType type) {
        return translationApiClient.translate(text, type)
                .map(translationResponseDTO -> translationResponseDTO.getContents().getTranslated());
    }
}
