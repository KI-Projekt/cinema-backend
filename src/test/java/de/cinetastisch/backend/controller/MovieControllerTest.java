package de.cinetastisch.backend.controller;





import de.cinetastisch.backend.dto.MovieRequestDto;
import de.cinetastisch.backend.dto.MovieResponseDto;
import de.cinetastisch.backend.dto.ScreeningResponseDto;
import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.enumeration.MovieStatus;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

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

//        Movie movie1 = new Movie("Test", "2000", "scr/test", MovieRating.G, "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200");
//        Movie movie2 = new Movie("Test", "2000", "scr/test", MovieRating.G, "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200");

        MovieResponseDto movie1 = new MovieResponseDto(1L, "Test", "2000", "scr/test", MovieRating.G.toString(), "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200", MovieStatus.IN_CATALOG);
        MovieResponseDto movie2 = new MovieResponseDto(2L, "Test", "2000", "scr/test", MovieRating.G.toString(), "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200", MovieStatus.IN_CATALOG);
        List<MovieResponseDto> expected = List.of(movie1, movie2);

        when(movieService.getAllMovies("Test", "", "", "")).thenReturn(expected);

        ResponseEntity<List<MovieResponseDto>> response = movieController.getAll("Test", "", "", "");
        assertAll(
                () -> assertEquals(response.getBody(), expected),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );
    }

    @Test
    void getOne() {
//        Movie movie1 = new Movie("Test", "2000", "scr/test", MovieRating.G, "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200");
        MovieResponseDto movie1 = new MovieResponseDto(1L, "Test", "2000", "scr/test", MovieRating.G.toString(), "200", "Action", "Nora", "klemp", "Kevin", "jsdflhjkfasdlhjkjflasdklkjfsda", "www.ded.de", "2/20", "2/20", "200", MovieStatus.IN_CATALOG);


        when(movieService.getMovie((long)1.222)).thenReturn(movie1);

        ResponseEntity<MovieResponseDto> response = movieController.getOne((long)1.222);
        assertAll(
                () -> assertEquals(response.getBody(), movie1),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );

    }

    @Test
    void addOne() {
        MovieResponseDto movieResponseDto = new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        MovieRequestDto movieRequestDto = new MovieRequestDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
//        Movie movie = new Movie("Avengers Endgame","2019","/src/datei.png", MovieRating.PG13,"120","Action","Anthony Russo","Christopher Markus","Chris Evens","Viel BumBum","www.youtube.com/Endgame","1234IMdb","27/10","1222");
        MovieResponseDto movie = new MovieResponseDto(1L, "Avengers Endgame","2019","/src/datei.png", MovieRating.PG13.toString(),"120","Action","Anthony Russo","Christopher Markus","Chris Evens","Viel BumBum","www.youtube.com/Endgame","1234IMdb","27/10","1222", MovieStatus.IN_CATALOG);

        when(movieService.addMovieByParameters(movieRequestDto,"AA123","Hangover")).thenReturn(movie);
        ResponseEntity<?> response = movieController.addOne(movieRequestDto,"AA123","Hangover");

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(movie,response.getBody());

    }

    @Test
    void replaceOne() {
        MovieResponseDto movieResponseDto = new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        MovieRequestDto movieRequestDto = new MovieRequestDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
//        Movie movie = new Movie("Avengers Endgame","2019","/src/datei.png", MovieRating.PG13,"120","Action","Anthony Russo","Christopher Markus","Chris Evens","Viel BumBum","www.youtube.com/Endgame","1234IMdb","27/10","1222");
        MovieResponseDto movie = new MovieResponseDto(1L, "Avengers Endgame","2019","/src/datei.png", MovieRating.PG13.toString(),"120","Action","Anthony Russo","Christopher Markus","Chris Evens","Viel BumBum","www.youtube.com/Endgame","1234IMdb","27/10","1222", MovieStatus.IN_CATALOG);


        when(movieService.replaceMovie((long)1.2, movieRequestDto)).thenReturn(movie);

        ResponseEntity<?> response = movieController.replaceOne((long)1.2, movieRequestDto);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void deleteOne() {
        ResponseEntity<?> response = movieController.deleteOne((long)1.2);

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    void archiveMovie() {
        ResponseEntity<?> response = movieController.archiveMovie((long)1.2);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void catalogMovie() {
        ResponseEntity<?> response = movieController.catalogMovie((long)1.2);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }


    // Deprecated! Replaced by GET /api/screenings?movie=...
//    @Test
//    void getScreenings() {
//        Screening firstScreening = new Screening(null,null,null,null,null);
//        Screening secoundScreening = new Screening(null,null,null,null,null);
//        List<ScreeningResponseDto> screeningList = List.of(firstScreening,secoundScreening);
//        when(movieService.getAllScreeningsByMovie((long)1.2)).thenReturn(screeningList);
//
//        ResponseEntity<?> response = movieController.getScreenings((long)1.2);
//        assertEquals(screeningList, response.getBody());
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//    }
}