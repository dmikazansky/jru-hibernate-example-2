package com.javarush.domain;

public enum Features {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");
    private final String value;
    Features(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public Features getFeaturesByValue(String value) {
        if (value == null || value.isEmpty()) return null;
        for (Features feature : Features.values()) {
            if (feature.getValue().equals(value)) {
                return feature;
            }
        }
        return null;
    }
//    set('Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes')
//    SET('TRAILERS', 'COMMENTARIES', 'DELETED SCENES', 'BEHIND THE SCENES')
}

