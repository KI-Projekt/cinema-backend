package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exeption.ResourceNotFoundException;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.pojo.MovieRequest;
import de.cinetastisch.backend.pojo.OmdbMovieResponse;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.ScreeningRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;

    public MovieService(MovieRepository movieRepository,
                        ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie getMovie(Long id){
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No movie with id [%s] found".formatted(id)));
    }

    public Movie getMovieByTitle(String title){
        return movieRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Movie Not Found."));
    }

    public List<Movie> getAllMoviesByTitle(String title){
        return movieRepository.findAllByTitle(title);
    }

    public Movie getMovieByImdbId(String imdbId){
        return movieRepository.findByImdbId(imdbId).orElseThrow();
    }

    public List<Movie> getAllMoviesByGenre(String genre){
        return movieRepository.findAllByGenre(genre);
    }

    public Movie addMovieDto(MovieRequest movie){
        return movieRepository.save(new Movie(
                movie.imdbId(), movie.releaseYear(), movie.posterImage(),
                movie.rated(), movie.runtime(), movie.genre(),
                movie.actors(), movie.plot(), movie.trailer(),
                movie.imdbId(), movie.imdbRating(),movie.imdbRatingCount()
        ));
    }

    public Movie addMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public Movie getOmdbMovieByTitle(String movieTitle){
        String uri = "https://www.omdbapi.com/?apikey=16be7c3b&t=" + movieTitle;
        return transformOmdbResponseToMovie(uri); // Optional: abstract to DAO ("dao.addMovie(movie)")?
    }

    public Movie getOmdbMovieByImdbId(String imdbId){
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

        System.out.println("SAVING " + movie.getTitle());
        return movie;
    }

    public Movie replaceMovie(Long id, Movie movie){
        Movie refMovie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No movie with id [%s] found".formatted(id)));
        movie.setId(refMovie.getId());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id){
        try {
            movieRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex){
            throw new ResourceNotFoundException("No Movie found with given ID");
        }
    }

    public List<Screening> getAllScreeningsByMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Movie Not Found")
        );
        return screeningRepository.findAllByMovie(movie);
    }


}
