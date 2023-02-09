package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.request.MovieRequestDto;
import de.cinetastisch.backend.dto.response.MovieResponseDto;
import de.cinetastisch.backend.dto.response.MovieSlimResponseDto;
import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.pojo.OmdbMovieResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class},
        imports = {MovieRating.class}
)
public interface MovieMapper {

    @Mapping(target = "movieStatus", ignore = true, defaultExpression = "java(MovieStatus.IN_CATALOG)")
    @Mapping(target = "rated", expression = "java(MovieRating.valueOfLabel(request.rated().substring(0, Math.min(5, request.length()))))")
    @Mapping(target = "id", ignore = true)
    Movie dtoToEntity(MovieRequestDto request);
    List<Movie> dtoToEntity(Iterable<MovieRequestDto> requests);

    @Mapping(target = "rated", expression = "java(MovieRating.valueOfLabel(response.getRated().substring(0, Math.min(5, response.length()))))")
    @Mapping(target = "movieStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trailer", ignore = true)
    @Mapping(target = "releaseYear", source = "year")
    @Mapping(target = "posterImage", source = "poster")
    @Mapping(target = "imdbRatingCount", source = "imdbVotes")
    @Mapping(target = "imdbId", source = "imdbID")
    Movie omdbMovieResponseToEntity(OmdbMovieResponse response);


    @Mapping(target = "id", expression = "java(movie.getId())")
    MovieResponseDto entityToDto(Movie movie);
    List<MovieResponseDto> entityToDto(Iterable<Movie> movies);

    @Mapping(target = "id", expression = "java(movie.id())")
    MovieSlimResponseDto trimDto(MovieResponseDto movie);
    List<MovieSlimResponseDto> trimDto(Iterable<MovieResponseDto> movie);

}
