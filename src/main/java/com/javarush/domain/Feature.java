package com.javarush.domain;

public enum Feature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");
    private final String value;
    private Feature(String value) {
        this.value = value;
    }
    public  String getValue() {
        return value;
    }

    public static Feature getFeaturesByValue(String value) {
        if (value == null || value.isEmpty()) return null;
        for (Feature feature : Feature.values()) {
            if (feature.getValue().equals(value)) {
                return feature;
            }
        }
        return null;
    }
}
