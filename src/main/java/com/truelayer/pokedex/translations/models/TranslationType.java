package com.truelayer.pokedex.translations.models;

public enum TranslationType {
    SHAKESPEARE("shakespeare"),
    YODA("yoda");

    private final String type;

    TranslationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
