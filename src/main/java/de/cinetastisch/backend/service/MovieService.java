package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exception.ResourceAlreadyExists;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
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

    public Movie getOneMovieByImdbId(String imdbId){
        return movieRepository.findByImdbIdIgnoreCase(imdbId)
                              .orElseThrow(() -> new ResourceNotFoundException("Movie Not Found."));
    }

    public Movie getOneMovieByTitle(String title){
        return movieRepository.findByTitleIgnoreCase(title)
                              .orElseThrow(() -> new ResourceNotFoundException("Movie Not Found."));
    }

    public List<Movie> getAllMoviesByTitle(String title){
        return movieRepository.findAllByTitleLikeIgnoreCase("%"+title+"%");
    }

    public List<Movie> getAllMoviesByGenre(String genre){
        return movieRepository.findAllByGenreLikeIgnoreCase("%"+genre+"%");
    }

    public Movie addMovie(Movie movie){
        if (movieRepository.existsById(movie.getId())){
            throw new ResourceAlreadyExists("Movie already exists");
        }
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

    public void checkIfTitleAlreadyExists(String title){
        if (movieRepository.existsByTitleIgnoreCase(title)){
            throw new ResourceAlreadyExists("Movie already exists");
        }
    }

    public void checkIfImdbIdAlreadyExists(String imdbId){
        if (movieRepository.existsByImdbIdIgnoreCase(imdbId)){
            throw new ResourceAlreadyExists("Movie already exists");
        }
    }

    public Movie addMovieByParameters(Movie movie, String imdbId, String title){
        if (imdbId != null && !imdbId.isBlank()) {
            checkIfImdbIdAlreadyExists(imdbId);
            Movie newMovie = getOmdbMovieByImdbId(imdbId);
            return addMovie(newMovie);

        } else if (title != null && !title.isBlank()) {
            title = title.replace("\"", "");
            checkIfTitleAlreadyExists(title);
            Movie newMovie = getOmdbMovieByTitle(title);
            checkIfTitleAlreadyExists(newMovie.getTitle());
            return addMovie(newMovie);

        } else if (movie != null && !movie.getTitle().equals("string")) {
            addMovie(movie);
        }

        throw new IllegalArgumentException("No inputs given");
    }

}
