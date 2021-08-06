package com.truelayer.pokedex.pokemon;

import com.truelayer.pokedex.UrlConfig;
import com.truelayer.pokedex.pokemon.models.PokemonSpeciesResponseDTO;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PokeApiClientTests {

    private static MockWebServer mockWebClient;

    @Value("classpath:mocks/poke-species-response.json")
    Resource mockPokeSpeciesResponse;

    @Mock
    private UrlConfig urlConfig;

    private PokeApiClient pokeApiClient;

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
        when(urlConfig.getPokeApi()).thenReturn(baseUrl);
        pokeApiClient = new PokeApiClient(urlConfig);
    }

    @Test
    void getSpecies_returns_pokemon_species_given_name() throws IOException {
        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(StreamUtils.copyToString(mockPokeSpeciesResponse.getInputStream(), Charset.defaultCharset()));
        mockWebClient.enqueue(mockResponse);

        PokemonSpeciesResponseDTO pokemonSpeciesResponseDTO = pokeApiClient.getSpecies("some-name").block();

        PokemonSpeciesResponseDTO expected = new PokemonSpeciesResponseDTO(
                "mewtwo",
                List.of(
                        new PokemonSpeciesResponseDTO.FlavorTextEntry(
                                "It was created by\na scientist after\nyears of horrific\fgene splicing and\nDNA engineering\nexperiments.",
                                new PokemonSpeciesResponseDTO.Language("en"))
                ),
                new PokemonSpeciesResponseDTO.Habitat("rare"),
                true
        );
        assertEquals(expected, pokemonSpeciesResponseDTO);
    }
}
