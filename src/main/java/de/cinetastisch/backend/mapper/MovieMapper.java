package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.MovieRequestDto;
import de.cinetastisch.backend.dto.MovieResponseDto;
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

    @Mapping(target = "movieStatus", ignore = true, defaultExpression = "java(MovieStatus.IN_CATALOG)")
    @Mapping(target = "rated", expression = "java(MovieRating.valueOfLabel(request.rated().substring(0,5)))")
    @Mapping(target = "id", ignore = true)
    Movie dtoToEntity(MovieRequestDto request);
    List<Movie> dtoToEntity(Iterable<MovieRequestDto> requests);

    @Mapping(target = "rated", expression = "java(MovieRating.valueOfLabel(response.getRated().substring(0,5)))")
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

}
