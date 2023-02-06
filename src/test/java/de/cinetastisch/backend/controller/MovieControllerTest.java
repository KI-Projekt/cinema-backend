package de.cinetastisch.backend.controller;


import de.cinetastisch.backend.dto.request.MovieRequestDto;
import de.cinetastisch.backend.dto.response.MovieResponseDto;
import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.enumeration.MovieStatus;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {


    @InjectMocks
    MovieController movieController;

    @Mock
    MovieService movieService;

    final Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
    final Specification<Movie> spec = null;

    @Test
    void getAllsuccessfull() {
        MovieResponseDto movie1 = new MovieResponseDto(1L, "Test", "2000", "scr/test", MovieRating.G.toString(), "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200", MovieStatus.IN_CATALOG);
        MovieResponseDto movie2 = new MovieResponseDto(2L, "Test", "2000", "scr/test", MovieRating.G.toString(), "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200", MovieStatus.IN_CATALOG);
        List<MovieResponseDto> expected = List.of(movie1, movie2);

        when(movieService.getAllMovies(null, pageable)).thenReturn(expected);

        ResponseEntity<List<MovieResponseDto>> response = movieController.getAll(spec, pageable);
        assertAll(
                () -> assertEquals(response.getBody(), expected),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );
    }

    @Test
    void getOne() {
        MovieResponseDto movie1 = new MovieResponseDto(1L, "Test", "2000", "scr/test", MovieRating.G.toString(), "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200", MovieStatus.IN_CATALOG);


        when(movieService.getMovie((long) 1.222)).thenReturn(movie1);

        ResponseEntity<MovieResponseDto> response = movieController.getOne((long) 1.222);
        assertAll(
                () -> assertEquals(response.getBody(), movie1),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );

    }

    @Test
    void addOne() {
        MovieResponseDto movieResponseDto = new MovieResponseDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        MovieRequestDto movieRequestDto = new MovieRequestDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        MovieResponseDto movie = new MovieResponseDto(1L, "Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13.toString(), "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222", MovieStatus.IN_CATALOG);

        when(movieService.addMovieByParameters(movieRequestDto, "AA123", "Hangover")).thenReturn(movie);
        ResponseEntity<?> response = movieController.addOne(movieRequestDto, "AA123", "Hangover");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(movie, response.getBody());

    }

    @Test
    void replaceOne() {
        MovieResponseDto movieResponseDto = new MovieResponseDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        MovieRequestDto movieRequestDto = new MovieRequestDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        MovieResponseDto movie = new MovieResponseDto(1L, "Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13.toString(), "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222", MovieStatus.IN_CATALOG);


        when(movieService.replaceMovie((long) 1.2, movieRequestDto)).thenReturn(movie);

        ResponseEntity<?> response = movieController.replaceOne((long) 1.2, movieRequestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteOne() {
        ResponseEntity<?> response = movieController.deleteOne((long) 1.2);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void archiveMovie() {
        ResponseEntity<?> response = movieController.archiveMovie((long) 1.2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void catalogMovie() {
        ResponseEntity<?> response = movieController.catalogMovie((long) 1.2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}


