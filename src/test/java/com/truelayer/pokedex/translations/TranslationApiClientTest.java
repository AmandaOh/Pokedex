package com.truelayer.pokedex.translations;

import com.truelayer.pokedex.UrlConfig;
import com.truelayer.pokedex.translations.models.TranslationResponseDTO;
import com.truelayer.pokedex.translations.models.TranslationType;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TranslationApiClientTest {

    private static MockWebServer mockWebClient;

    @Value("classpath:mocks/shakespeare-translation-response.json")
    Resource mockTranslationResponse;

    @Mock
    private UrlConfig urlConfig;

    private TranslationApiClient translationApiClient;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebClient = new MockWebServer();
        mockWebClient.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebClient.shutdown();
    }

    @BeforeEach
    void init() {
        String baseUrl = String.format("http://localhost:%s", mockWebClient.getPort());
        when(urlConfig.getTranslationsApi()).thenReturn(baseUrl);
        translationApiClient = new TranslationApiClient(urlConfig);
    }

    @Test
    void translate_returns_translated_text() throws Exception {
        String text = "It was created by a scientist after years of horrific gene splicing and DNA engineering experiments.";
        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(StreamUtils.copyToString(mockTranslationResponse.getInputStream(), Charset.defaultCharset()));
        mockWebClient.enqueue(mockResponse);

        TranslationResponseDTO actual = translationApiClient.translate(text, TranslationType.SHAKESPEARE).block();

        TranslationResponseDTO expected = new TranslationResponseDTO(
                new TranslationResponseDTO.Content(
                        "'t wast did create by a scientist after years of horrific gene splicing and dna engineering experiments."
                )
        );
        assertEquals(expected, actual);
    }
}