package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAll() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getOne(@PathVariable("id") Long id){
        return movieService.getMovie(id);
    }

    @GetMapping("/search")              // GET http://localhost:8080/api/movies/search?title=Guardians of the Galaxy
    public Movie getOneByTitle(@RequestParam("title") String movieTitle){
        return movieService.getMovieByTitle(movieTitle);
    }

    @PostMapping("/add")                // POST http://localhost:8080/api/movies/add?title=Guardians of the Galaxy
    public ResponseEntity<Movie> addOne(@Valid @RequestParam(value = "imdbId", required = false) String imdbId,
                                        @Valid @RequestParam(value = "title", required = false) String movieTitle){
        Movie newMovie;
        Movie existingMovie;

        if (imdbId != null && !imdbId.isBlank()) {      // If imdbID is given

            existingMovie = movieService.getMovieByImdbId(imdbId);
            if (existingMovie != null){
                return new ResponseEntity<>(existingMovie, HttpStatus.CONFLICT);
            }

            newMovie = movieService.addMovieByImdbId(imdbId);
            return new ResponseEntity<>(movieService.addMovie(newMovie), HttpStatus.CREATED);

        } else if (movieTitle != null && !movieTitle.isBlank()) {   // If only movieTitle is given
            movieTitle = movieTitle.replace("\"", "");
            existingMovie = movieService.getMovieByTitle(movieTitle); // Dieser Check ist zu streng, TODO: Fuzzy-Search?

            if (existingMovie != null){
                return new ResponseEntity<>(existingMovie, HttpStatus.CONFLICT);
                // The 409 (Conflict) status code indicates that the request could not be completed due to a conflict with the current state of the target resource.
            }

            newMovie = movieService.addMovieByTitle(movieTitle);

            // Secondary Check
            existingMovie = movieService.getMovieByTitle(newMovie.getTitle());
            if (existingMovie != null){
                return new ResponseEntity<>(existingMovie, HttpStatus.CONFLICT);
            }

            return new ResponseEntity<>(movieService.addMovie(newMovie), HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build(); // If none given
    }

    @PutMapping("/{id}")
    public void replaceOne(@PathVariable Long id, @RequestBody Movie movie){
        movieService.replaceMovie(id, movie);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") Long id){
        movieService.deleteMovie(id);
    }

    @GetMapping("{id}/screenings") //TODO: set timespan
    public List<Screening> getScreenings(@PathVariable("id") Long id){
        return movieService.getScreeningsOfMovie(id);
    }
}
