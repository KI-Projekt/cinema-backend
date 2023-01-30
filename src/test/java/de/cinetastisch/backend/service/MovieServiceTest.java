package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.MovieRequestDto;
import de.cinetastisch.backend.dto.response.MovieResponseDto;
import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.enumeration.MovieStatus;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceHasChildrenException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.MovieMapper;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @InjectMocks
    MovieService movieService;
    @Mock
    MovieRepository movieRepository;
    @Mock
    ScreeningRepository screeningRepository;
    @Mock
    MovieMapper movieMapper;

//     final String url = "https://www.omdbapi.com/?apikey=16be7c3b&i="+ "tt0076759";
//    RestTemplate restTemplate = new RestTemplate();
//    @Mock
//    OmdbMovieResponse omdbMovieResponse = restTemplate.getForObject(url, OmdbMovieResponse.class);



    @Test
    void getAllMoviesthowsexeption() {
        assertThrows(IllegalArgumentException.class, () -> movieService.getAllMovies("Hangover", "Action", "1234IMdb", "27/10"));
    }

    @Test
    void getAllMoviesByImdbID() {
        String ImdbID = "1234IMdb";
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,ImdbID,null,null,null);
        List<MovieResponseDto> responseDtoList = List.of(responseDto,responseDto);

        Movie firstmovie = new Movie("Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", ImdbID, "27/10", "1222");
        Movie secondmovie = new Movie("Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", ImdbID, "27/10", "1222");
        List<Movie> movieList = List.of(firstmovie, secondmovie);


        when(movieRepository.findAllByImdbIdLikeIgnoreCase("%" + ImdbID + "%")).thenReturn(movieList);
        when(movieMapper.entityToDto(movieList)).thenReturn(responseDtoList);
        List<?> response = movieService.getAllMovies(null, null, ImdbID, null);
        assertEquals(responseDtoList, response);
    }

    @Test
    void getAllMoviesByTitle() {
        String title = "Avengers Engame";
        Movie movie = new Movie(title, "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222");
        List<Movie> movieList = List.of(movie,movie);
        MovieResponseDto responseDto =new MovieResponseDto(null,title,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        List<MovieResponseDto> responseDtoList = List.of(responseDto,responseDto);

        when(movieRepository.findAllByTitleLikeIgnoreCase("%" + title + "%")).thenReturn(movieList);
        when(movieMapper.entityToDto(movieList)).thenReturn(responseDtoList);

        List<MovieResponseDto> response = movieService.getAllMovies(title, null, null, null);
        assertEquals(responseDtoList, response);
    }

    @Test
    void getAllMoviesimdbByGerne() {
        String genre = "Action";
        Movie movie = new Movie("Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13, "120", genre, "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222");
        List<Movie> movieList = List.of(movie,movie);
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,genre,null,null,null,null,null,null,null,null,null);
        List<MovieResponseDto> responseDtoList = List.of(responseDto,responseDto);

        when(movieRepository.findAllByGenreLikeIgnoreCase("%" + genre + "%")).thenReturn(movieList);
        when(movieMapper.entityToDto(movieList)).thenReturn(responseDtoList);

        List<MovieResponseDto> response = movieService.getAllMovies(null, genre, null, null);
        assertEquals(responseDtoList, response);
    }

    @Test
    void getAllMoviesByrated() {
        String rated = "PG-13";
        MovieRating movieRating = MovieRating.valueOfLabel(rated);
        Movie movie = new Movie("Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222");
        List<Movie> movieList = List.of(movie,movie);
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,rated,null,null,null,null,null,null,null,null,null,null,null);
        List<MovieResponseDto> responseDtoList = List.of(responseDto,responseDto);

        when(movieRepository.findAllByRatedLessThanEqual(movieRating)).thenReturn(movieList);
        when(movieMapper.entityToDto(movieList)).thenReturn(responseDtoList);

        List<MovieResponseDto> response = movieService.getAllMovies(null, null, null, rated);
        assertEquals(responseDtoList, response);

    }

    @Test
    void getAllMovies() {
        Movie movie = new Movie("Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222");
        List<Movie> movieList = List.of(movie,movie);
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        List<MovieResponseDto> responseDtoList = List.of(responseDto,responseDto);

        when(movieRepository.findAll()).thenReturn(movieList);
        when(movieMapper.entityToDto(movieList)).thenReturn(responseDtoList);

        List<MovieResponseDto> response = movieService.getAllMovies(null, null, null, null);
        assertEquals(responseDtoList, response);

    }

    @Test void getMovie(){
        Movie movie = new Movie("Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222");
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        when(movieRepository.getReferenceById((long)1.2)).thenReturn(movie);
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        MovieResponseDto response = movieService.getMovie((long)1.2);

        assertEquals(responseDto, response);

    }

    @Test
    void addMoviesetnoMovieStatus() {
        String test = "Test";
        Movie movie = new Movie(test, "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",test , "27/10", "1222");

        when(movieRepository.existsByImdbIdIgnoreCase(test)).thenReturn(false);
        when(movieRepository.existsByTitleIgnoreCase(test)).thenReturn(false);
        when(movieRepository.save(movie)).thenReturn(movie);
        movie.setMovieStatus(null);
        Movie respone = movieService.addMovie(movie);

        assertEquals(movie,respone);
    }
    @Test
    void addMovie() {
        String test = "Test";
        Movie movie = new Movie(test, "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",test , "27/10", "1222");

        when(movieRepository.existsByImdbIdIgnoreCase(test)).thenReturn(false);
        when(movieRepository.existsByTitleIgnoreCase(test)).thenReturn(false);
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie response = movieService.addMovie(movie);
        assertEquals(movie,response);
    }

    @Test
    void addMovieByParametersMovieDto() {
        MovieRequestDto requestDto = new MovieRequestDto(null,null,null,"PG-13",null,null,null,null,null,null,null,null,null,null,null);
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222");
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        when(movieMapper.dtoToEntity(requestDto)).thenReturn(movie);
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        when(movieService.addMovie(movie)).thenReturn(movie);
        MovieResponseDto response = movieService.addMovieByParameters(requestDto,null,"");

        assertEquals(responseDto,response);

    }
    @Test
    void addMovieByParametersMovieDtoExeption() {
        MovieRequestDto requestDto = new MovieRequestDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

        assertThrows(IllegalArgumentException.class,()->movieService.addMovieByParameters(requestDto,null,null));

    }
    @Test
    void addMovieByParametersTitle() {

//
//        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222");
//        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
//
//
//
//            when(movieRepository.existsByImdbIdIgnoreCase("tt0076759")).thenReturn(false);
//            when(movieService.getOmdbMovieByImdbId("tt0076759")).thenReturn(movie);
//            when(movieService.transformOmdbResponseToMovie("www.test.de")).thenReturn(movie);
//            when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
//            when(movieService.addMovie(movie)).thenReturn(movie);
//            MovieResponseDto response = movieService.addMovieByParameters(null, "tt0076759", "");
//            assertEquals(responseDto,response);




    }
    @Test
    void addMovieByParametersImdb_Id() {}
    @Test
    void addMovieByParametersallNull() {}


    @Test
    void replaceMovie() {
        MovieRequestDto requestDto = new MovieRequestDto(null,null,null,"PG-13",null,null,null,null,null,null,null,null,null,null,null);
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222");
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

        when(movieMapper.dtoToEntity(requestDto)).thenReturn(movie);
        when(movieRepository.getReferenceById((long)1.2)).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);

        MovieResponseDto respone = movieService.replaceMovie((long)1.2,requestDto);

        assertEquals(responseDto,respone);
    }

    @Test
    void deleteMovieExeption() {
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222");

        when(movieRepository.getReferenceById((long)1.2)).thenReturn(movie);
        when(screeningRepository.existsByMovie(movie)).thenReturn(true);

        assertThrows(ResourceHasChildrenException.class,()->movieService.deleteMovie((long)1.2));


    }
    @Test
    void deleteMovie() {
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222");

        when(movieRepository.getReferenceById((long)1.2)).thenReturn(movie);
        when(screeningRepository.existsByMovie(movie)).thenReturn(false);
        movieService.deleteMovie((long)1.2);

    }

    @Test
    void getOmdbMovieByTitle() {
    }

    @Test
    void getOmdbMovieByImdbId() {
    }

    @Test
    void transformOmdbResponseToMovie() {
        assertThrows(ResourceNotFoundException.class,()->movieService.transformOmdbResponseToMovie("https://www.omdbapi.com/?apikey=16be7c3b&i=tt12345"));
    }

    @Test
    void checkIfTitleAlreadyExistsTrue() {
        when(movieRepository.existsByTitleIgnoreCase("Star Wars")).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class,()-> movieService.checkIfTitleAlreadyExists("Star Wars"));
    }
    @Test
    void checkIfTitleAlreadyExistsFalse() {
        when(movieRepository.existsByTitleIgnoreCase("Star Wars")).thenReturn(false);
        assertDoesNotThrow(()-> movieService.checkIfTitleAlreadyExists("Star Wars"));
    }

    @Test
    void checkIfImdbIdAlreadyExistsTrue() {
        when(movieRepository.existsByImdbIdIgnoreCase("AA123")).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class, ()->movieService.checkIfImdbIdAlreadyExists("AA123"));
    }
    @Test
    void checkIfImdbIdAlreadyExistsFalse() {
        when(movieRepository.existsByImdbIdIgnoreCase("AA123")).thenReturn(false);
        assertDoesNotThrow(()->movieService.checkIfImdbIdAlreadyExists("AA123"));
    }


    @Test
    void archiveChanceMovieStatus() {
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222");
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        when(movieRepository.findById((long)1.2)).thenReturn(Optional.of(movie));
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        when(movieRepository.save(movie)).thenReturn(movie);
        movie.setMovieStatus(MovieStatus.IN_CATALOG);

        MovieResponseDto response = movieService.archive((long)1.2);

        assertEquals(responseDto,response);

    }
    @Test
    void archiveChanceMovieStatusNot() {
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", null, "27/10", "1222");
        MovieResponseDto responseDto = new MovieResponseDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        when(movieRepository.findById((long) 1.2)).thenReturn(Optional.of(movie));
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        movie.setMovieStatus(MovieStatus.ARCHIVED);

        MovieResponseDto response = movieService.archive((long) 1.2);

        assertEquals(responseDto, response);
    }

    @Test
    void catalogChangeMovieStatusNot() {
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", null, "27/10", "1222");
        MovieResponseDto responseDto = new MovieResponseDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        when(movieRepository.findById((long) 1.2)).thenReturn(Optional.of(movie));
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        movie.setMovieStatus(MovieStatus.IN_CATALOG);

        MovieResponseDto response = movieService.catalog((long) 1.2);

        assertEquals(responseDto, response);

    }
    @Test
    void catalogChangeMovieStatus(){
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222");
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        when(movieRepository.findById((long)1.2)).thenReturn(Optional.of(movie));
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        when(movieRepository.save(movie)).thenReturn(movie);
        movie.setMovieStatus(MovieStatus.ARCHIVED);

        MovieResponseDto response = movieService.catalog((long)1.2);

        assertEquals(responseDto,response);
    }
}