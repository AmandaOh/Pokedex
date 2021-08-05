package com.truelayer.pokedex;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("url")
public class UrlConfig {

    private String pokeApi;

    public String getPokeApi() {
        return pokeApi;
    }

    public void setPokeApi(String pokeApi) {
        this.pokeApi = pokeApi;
    }
}
