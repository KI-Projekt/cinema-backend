package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exception.ResourceAlreadyExists;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.MovieMapper;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.pojo.OmdbMovieResponse;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;



@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    private final MovieMapper mapper;


    public MovieService(MovieRepository movieRepository,
                        ScreeningRepository screeningRepository,
                        MovieMapper mapper) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.mapper = mapper;
    }


    // #########################
    // CRUD
    // #########################


    public List<Movie> getAllMovies(String title, String genre, String imdbId){
        if (title != null && !title.isBlank() && genre != null && !genre.isBlank()){
            throw new IllegalArgumentException("Only one query parameter at a time supported.");
        }

        if(imdbId != null && !imdbId.isBlank()){
            return movieRepository.findAllByImdbIdLikeIgnoreCase("%" + imdbId + "%");
        } else if(title != null && !title.isBlank()){
            return movieRepository.findAllByTitleLikeIgnoreCase("%"+title+"%");
        } else if (genre != null && !genre.isBlank()){
            return movieRepository.findAllByGenreLikeIgnoreCase("%"+genre+"%");
        } else {
            return movieRepository.findAll();
        }

//        List<MovieDto>responseDto = response.stream().map(this::convertToDto).toList();
    }

    public Movie getMovie(Long id){
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No movie with id [%s] found".formatted(id)));
    }

    public Movie addMovie(Movie movie){
        checkIfImdbIdAlreadyExists(movie.getImdbId());
        checkIfTitleAlreadyExists(movie.getTitle());
        return movieRepository.save(movie);
    }

    public Movie addMovieByParameters(Movie movie, String imdbId, String title){
        if (movie != null){
            if(movie.getId() != null) {
                throw new IllegalArgumentException("Please don't specify an id.");
            }
            return movieRepository.save(movie);
        }

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

        }

        throw new IllegalArgumentException("No inputs given");
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


    // ##############
    // Helper
    // ##############

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

        return mapper.omdbMovieResponseToEntity(omdbMovieResponse);
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



}
