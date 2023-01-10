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
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class})
public interface MovieMapper {
    @Mapping(target = "writer", ignore = true)
    @Mapping(target = "trailer", ignore = true)
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "runtime", ignore = true)
    @Mapping(target = "releaseYear", ignore = true)
    @Mapping(target = "rated", ignore = true)
    @Mapping(target = "posterImage", ignore = true)
    @Mapping(target = "plot", ignore = true)
    @Mapping(target = "movieStatus", ignore = true)
    @Mapping(target = "imdbRatingCount", ignore = true)
    @Mapping(target = "imdbRating", ignore = true)
    @Mapping(target = "imdbId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genre", ignore = true)
    @Mapping(target = "director", ignore = true)
    @Mapping(target = "actors", ignore = true)
    Movie toEntity(Long id);

    @Mapping(target = "id", ignore = true)
    Movie dtoToEntity(MovieRequestDto movieRequestDto);

    List<Movie> dtoToEntity(Iterable<MovieRequestDto> movieDto);

    @Mapping(target = "rated", expression = "java(MovieRating.valueOfLabel(response.getRated().substring(0,5)))")
    @Mapping(target = "movieStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trailer", ignore = true)
    @Mapping(target = "releaseYear", source = "year")
    @Mapping(target = "posterImage", source = "poster")
    @Mapping(target = "imdbRatingCount", source = "imdbVotes")
    @Mapping(target = "imdbId", source = "imdbID")
    Movie omdbMovieResponseToEntity(OmdbMovieResponse response);
}
