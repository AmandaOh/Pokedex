package com.truelayer.pokedex.translations;

import com.truelayer.pokedex.translations.models.TranslationResponseDTO;
import com.truelayer.pokedex.translations.models.TranslationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {

    @Mock
    private TranslationApiClient translationApiClient;

    @InjectMocks
    private TranslationService translationService;

    private TranslationResponseDTO translationResponseDTO;

    @BeforeEach
    void setUp() {
        translationResponseDTO = new TranslationResponseDTO(
                new TranslationResponseDTO.Content("Translated text")
        );
    }

    @Test
    void getTranslation_returns_translated_content() {
        String text = "Some text";
        when(translationApiClient.translate(text, TranslationType.YODA)).thenReturn(
                Mono.just(translationResponseDTO)
        );

        String actual = translationService.getTranslation(text, TranslationType.YODA).block();

        assertEquals("Translated text", actual);
    }

    @Test
    void getTranslation_returns_original_description_if_translation_request_fails() {
        String text = "Some text";
        when(translationApiClient.translate(text, TranslationType.YODA)).thenReturn(
                Mono.error(WebClientResponseException.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Some error occurred", null, null, null))
        );

        String actual = translationService.getTranslation(text, TranslationType.YODA).block();

        assertEquals(text, actual);
    }
}