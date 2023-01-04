package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.MovieDto;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.pojo.OmdbMovieResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieMapper {

    Movie dtoToEntity(MovieDto movieDto);
    List<Movie> dtoToEntity(Iterable<MovieDto> movieDto);

    MovieDto entityToDto(Movie movie);
    List<MovieDto> entityToDto(Iterable<Movie> movie);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trailer", ignore = true)
    @Mapping(target = "releaseYear", source = "year")
    @Mapping(target = "posterImage", source = "poster")
    @Mapping(target = "imdbRatingCount", source = "imdbVotes")
    @Mapping(target = "imdbId", source = "imdbID")
    Movie omdbMovieResponseToEntity(OmdbMovieResponse response);
}
