package com.truelayer.pokedex.pokemon.models;

public enum Language {
    ENGLISH("en");

    private final String name;

    Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
