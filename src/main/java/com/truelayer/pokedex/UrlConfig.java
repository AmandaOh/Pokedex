package com.truelayer.pokedex;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("url")
@Getter
@NoArgsConstructor
@Setter
public class UrlConfig {

    private String pokeApi;
    private String translationsApi;
}
