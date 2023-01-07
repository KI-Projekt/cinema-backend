package de.cinetastisch.backend.converter;

import de.cinetastisch.backend.enumeration.MovieRating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class MovieRatingConverter implements AttributeConverter<MovieRating, Integer> {
    @Override
    public Integer convertToDatabaseColumn(MovieRating attribute) {
        if (attribute == null){
            return null;
        }
        return attribute.order;
    }

    @Override
    public MovieRating convertToEntityAttribute(Integer dbData) {
        if (dbData == null){
            return null;
        }

        return Stream.of(MovieRating.values())
                     .filter(c -> c.order == dbData)
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new);
    }
}
