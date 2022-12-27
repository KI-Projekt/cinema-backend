package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/movies")
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

    @PostMapping
    public Movie addOne(@RequestBody Movie movie){
        return movieService.addMovie(movie);
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
