package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exeption.ResourceNotFoundException;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie getMovie(Long id){
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No movie with id [%s] found".formatted(id)));
    }

    public void addMovie(Movie movie){
        movieRepository.save(movie); // Optional: abstract to DAO ("dao.addMovie(movie)")?
    }

    public Iterable<Movie> addMovies(List<Movie> movies){
        return movieRepository.saveAll(movies);
    }

    public void replaceMovie(Long id, Movie movie){
//        Movie oldMovie = movieRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("ID not found"));
        Movie refMovie = movieRepository.getReferenceById(id);
        // TODO: add checks
        refMovie.setTitle(movie.getTitle());
        movieRepository.save(refMovie);
    }

    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
    }
}
