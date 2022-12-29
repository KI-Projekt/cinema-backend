package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exeption.ResourceNotFoundException;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.pojo.OmdbMovieResponse;
import de.cinetastisch.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public Movie getMovieByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    public Movie getMovieByImdbId(String imdbId){
        return movieRepository.findByImdbId(imdbId);
    }

    public List<Movie> getAllMoviesByGenre(String genre){
        return movieRepository.findAllByGenre(genre);
    }

    public Movie addMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public Movie addMovieByTitle(String movieTitle){
        String uri = "https://www.omdbapi.com/?apikey=16be7c3b&t=\"" + movieTitle + "\"";
        return transformOmdbResponseToMovie(uri); // Optional: abstract to DAO ("dao.addMovie(movie)")?
    }

    public Movie addMovieByImdbId(String imdbId){
        String uri = "https://www.omdbapi.com/?apikey=16be7c3b&i=" + imdbId;
        return transformOmdbResponseToMovie(uri);
    }

    public Movie transformOmdbResponseToMovie(String uri){
        RestTemplate restTemplate = new RestTemplate();
        OmdbMovieResponse omdbMovieResponse = restTemplate.getForObject(uri, OmdbMovieResponse.class);

        if (omdbMovieResponse.getResponse().equals("False")){
            throw new ResourceNotFoundException("No Movie found");
        }

        Movie movie = new Movie(
                omdbMovieResponse.getTitle(),
                omdbMovieResponse.getYear(),
                omdbMovieResponse.getPoster(),
                omdbMovieResponse.getRated(),
                omdbMovieResponse.getRuntime(),
                omdbMovieResponse.getGenre(),
                omdbMovieResponse.getActors(),
                omdbMovieResponse.getPlot(),
                "TODO",
                omdbMovieResponse.getImdbID(),
                omdbMovieResponse.getImdbRating(),
                omdbMovieResponse.getImdbVotes()
        );

        System.out.println("SAVING " + movie.getTitle());;
        return movie;
    }

    public void replaceMovie(Long id, Movie movie){
        Movie refMovie = movieRepository.getReferenceById(id);
        refMovie.setTitle(movie.getTitle());
        movieRepository.save(refMovie);
    }

    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
    }

    public List<Screening> getScreeningsOfMovie(Long id) {
        return movieRepository.getScreenings(id);
    }


}
