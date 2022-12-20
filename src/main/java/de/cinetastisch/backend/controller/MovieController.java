package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Movie;
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
    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable("id") Long id){
        return movieService.getMovie(id);
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie){
        movieService.addMovie(movie);
    }

    @PutMapping("/{id}")
    public void replaceMovie(@PathVariable Long id, @RequestBody Movie movie){
        movieService.replaceMovie(id, movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable("id") Long id){
        movieService.deleteMovie(id);
    }
}
