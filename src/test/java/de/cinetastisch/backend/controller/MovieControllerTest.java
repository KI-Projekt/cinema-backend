package de.cinetastisch.backend.controller;





import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {


    @InjectMocks
    MovieController movieController;

    @Mock
    MovieService movieService;

    @Test
    void getAllsuccessfull() {

        Movie movie1 = new Movie("Test", "2000", "scr/test", MovieRating.G, "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200");
        Movie movie2 = new Movie("Test", "2000", "scr/test", MovieRating.G, "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200");
        List<Movie> expected = List.of(movie1, movie2);

        when(movieService.getAllMovies("Test", "", "", "")).thenReturn(expected);

        ResponseEntity<List<Movie>> response = movieController.getAll("Test", "", "", "");
        assertAll(
                () -> assertEquals(response.getBody(), expected),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );
    }

    // TODO: 24.01.2023 Warum hat das Leeren Body
    @Test
    void getallnotsuccessfull() {
//        List<Movie> expected = List.of();
//        when(movieService.getAllMovies("Test", "", "", "")).thenReturn(expected);
//
//        ResponseEntity<List<Movie>> response = movieController.getAll("Test", "", "", "");
//        assertAll(
//                ()-> assertEquals(response.getBody(),expected),
//                (-> assertEquals(response.getStatusCode());)
//        );
//
    }

    @Test
    void getOne() {
        Movie movie1 = new Movie("Test", "2000", "scr/test", MovieRating.G, "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200");


        when(movieService.getMovie((long)1.222)).thenReturn(movie1);

        ResponseEntity<Movie> response = movieController.getOne((long)1.222);
        assertAll(
                () -> assertEquals(response.getBody(), movie1),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );

    }

    @Test
    void addOne() {
    }

    @Test
    void replaceOne() {
    }

    @Test
    void deleteOne() {
    }

    @Test
    void archiveMovie() {
    }

    @Test
    void catalogMovie() {
    }

    @Test
    void getScreenings() {
    }
}