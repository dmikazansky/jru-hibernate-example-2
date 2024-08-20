package com.javarush.converters;

import com.javarush.domain.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating category) {
        if (category == null) {
            return null;
        }
        return category.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Rating.values())
                .filter(c -> c.getValue().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}