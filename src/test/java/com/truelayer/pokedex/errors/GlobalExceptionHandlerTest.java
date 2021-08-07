package com.truelayer.pokedex.errors;

import com.truelayer.pokedex.PokedexController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @Mock
    PokedexController pokedexController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pokedexController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void handleNotFoundException_returns_api_error_when_web_client_returns_not_found_exception() throws Exception {
        when(pokedexController.getPokemon("wrong-name")).thenThrow(
                WebClientResponseException.create(HttpStatus.NOT_FOUND.value(), "Not Found.", null, null, null)
        );

        mockMvc.perform(get("/pokemon/wrong-name"))
                .andExpect(status().isNotFound());
    }
}