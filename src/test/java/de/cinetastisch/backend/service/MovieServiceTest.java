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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    final Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
    final Specification<Movie> spec = null;
    final Movie movie1 = new Movie("Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222", MovieStatus.IN_CATALOG);
    final MovieResponseDto responseDto = new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);


    @Test
    void canGetAllStudents(){
        //when
        movieService.getAllMovies(spec, pageable);
        //then
        verify(movieRepository).findAll(spec, pageable.getSort());
    }




    @Test
    void getAllMovies() {
        //Given (Arrange)
        List<Movie> movieList = List.of(movie1,movie1);
        List<MovieResponseDto> responseDtoList = List.of(responseDto,responseDto);
        //When (Act)
        when(movieRepository.findAll(spec, pageable.getSort())).thenReturn(movieList);
        when(movieMapper.entityToDto(movieList)).thenReturn(responseDtoList);
        List<MovieResponseDto> response = movieService.getAllMovies(null, pageable);

        //Then (Assert)
        assertEquals(responseDtoList, response);
    }

    @Test
    void getMovie(){
        Movie movie = new Movie("Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222", MovieStatus.IN_CATALOG);
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        when(movieRepository.getReferenceById((long)1.2)).thenReturn(movie);
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        MovieResponseDto response = movieService.getMovie((long)1.2);

        assertEquals(responseDto, response);

    }

    @Test
    void addMoviesetnoMovieStatus() {
        String test = "Test";
        Movie movie = new Movie(test, "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",test , "27/10", "1222", MovieStatus.IN_CATALOG);

        when(movieRepository.existsByImdbIdIgnoreCase(test)).thenReturn(false);
        when(movieRepository.existsByTitleIgnoreCase(test)).thenReturn(false);
        when(movieRepository.save(movie)).thenReturn(movie);
        movie.setMovieStatus(null);
        Movie response = movieService.addMovie(movie);

        assertEquals(movie,response);
    }
    @Test
    void addMovie() {
        String test = "Test";
        Movie movie = new Movie(test, "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",test , "27/10", "1222", MovieStatus.IN_CATALOG);

        when(movieRepository.existsByImdbIdIgnoreCase(test)).thenReturn(false);
        when(movieRepository.existsByTitleIgnoreCase(test)).thenReturn(false);
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie response = movieService.addMovie(movie);
        assertEquals(movie,response);
    }

    @Test
    void addMovieByParametersMovieDto() {
        MovieRequestDto requestDto = new MovieRequestDto(null,null,null,"PG-13",null,null,null,null,null,null,null,null,null,null,null);
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222", MovieStatus.IN_CATALOG);
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
        MovieRequestDto requestDto = new MovieRequestDto(null,null,null,"PG-13",null,null,null,null,null,null,null,null,null,null,null);
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222", MovieStatus.IN_CATALOG);
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        when(movieMapper.dtoToEntity(requestDto)).thenReturn(movie);
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        when(movieService.addMovie(movie)).thenReturn(movie);
        MovieResponseDto response = movieService.addMovieByParameters(requestDto,null,"");

        assertEquals(responseDto,response);


    }
    @Test
    void addMovieByParametersImdb_Id() {}
    @Test
    void addMovieByParametersallNull() {}


    @Test
    void replaceMovie() {
        MovieRequestDto requestDto = new MovieRequestDto(null,null,null,"PG-13",null,null,null,null,null,null,null,null,null,null,null);
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222", MovieStatus.IN_CATALOG);
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
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222", MovieStatus.IN_CATALOG);

        when(movieRepository.getReferenceById((long)1.2)).thenReturn(movie);
        when(screeningRepository.existsByMovie(movie)).thenReturn(true);

        assertThrows(ResourceHasChildrenException.class,()->movieService.deleteMovie((long)1.2));


    }
    @Test
    void deleteMovie() {
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222", MovieStatus.IN_CATALOG);

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
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222", MovieStatus.IN_CATALOG);
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
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", null, "27/10", "1222", MovieStatus.IN_CATALOG);
        MovieResponseDto responseDto = new MovieResponseDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        when(movieRepository.findById((long) 1.2)).thenReturn(Optional.of(movie));
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        movie.setMovieStatus(MovieStatus.ARCHIVED);

        MovieResponseDto response = movieService.archive((long) 1.2);

        assertEquals(responseDto, response);
    }

    @Test
    void catalogChangeMovieStatusNot() {
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", null, "27/10", "1222", MovieStatus.IN_CATALOG);
        MovieResponseDto responseDto = new MovieResponseDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        when(movieRepository.findById((long) 1.2)).thenReturn(Optional.of(movie));
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        movie.setMovieStatus(MovieStatus.IN_CATALOG);

        MovieResponseDto response = movieService.catalog((long) 1.2);

        assertEquals(responseDto, response);

    }
    @Test
    void catalogChangeMovieStatus(){
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame",null , "27/10", "1222", MovieStatus.IN_CATALOG);
        MovieResponseDto responseDto =new MovieResponseDto(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        when(movieRepository.findById((long)1.2)).thenReturn(Optional.of(movie));
        when(movieMapper.entityToDto(movie)).thenReturn(responseDto);
        when(movieRepository.save(movie)).thenReturn(movie);
        movie.setMovieStatus(MovieStatus.ARCHIVED);

        MovieResponseDto response = movieService.catalog((long)1.2);

        assertEquals(responseDto,response);
    }
}