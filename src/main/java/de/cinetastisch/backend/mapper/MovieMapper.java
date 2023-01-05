package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.MovieRequestDto;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.pojo.OmdbMovieResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MovieMapper {

    @Mapping(target = "id", ignore = true)
    Movie dtoToEntity(MovieRequestDto movieRequestDto);
    List<Movie> dtoToEntity(Iterable<MovieRequestDto> movieDto);

    @Mapping(target = "movieStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trailer", ignore = true)
    @Mapping(target = "releaseYear", source = "year")
    @Mapping(target = "posterImage", source = "poster")
    @Mapping(target = "imdbRatingCount", source = "imdbVotes")
    @Mapping(target = "imdbId", source = "imdbID")
    Movie omdbMovieResponseToEntity(OmdbMovieResponse response);
}
