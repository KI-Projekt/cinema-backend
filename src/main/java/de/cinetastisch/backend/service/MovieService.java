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
import de.cinetastisch.backend.pojo.OmdbMovieResponse;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final ScreeningRepository screeningRepository;

    @Transactional
    public List<MovieResponseDto> getAllMovies(Specification<Movie> spec, Pageable pageable) {

        Sort sort = pageable.getSort();
        if ( sort.isEmpty() ){
            sort = Sort.by("id");
        }
        List<Movie> result = movieRepository.findAll(spec, sort);
        return movieMapper.entityToDto(result);
    }

    public List<MovieResponseDto> getAllMoviesForReview(){
        return movieMapper.entityToDto(movieRepository.findAllByForReview(true));
    }

    public MovieResponseDto getMovie(Long id){
        return movieMapper.entityToDto(movieRepository.getReferenceById(id));
    }

    public Movie addMovie(Movie movie){
        checkIfImdbIdAlreadyExists(movie.getImdbId());
        checkIfTitleAlreadyExists(movie.getTitle());
        if (movie.getMovieStatus() == null){
            movie.setMovieStatus(MovieStatus.IN_CATALOG);
        }
        return movieRepository.save(movie);
    }

    public MovieResponseDto addMovieByParameters(MovieRequestDto movie, String imdbId, String title){
        //TODO: api endpoint for AI to add movie

        if (movie != null){
            if(MovieRating.valueOfLabel(movie.rated()) == null){
                throw new IllegalArgumentException("Wrong Movie Rating given, optional are: " + Arrays.toString(MovieRating.getLabels()));
            }
            Movie newMovie = movieMapper.dtoToEntity(movie);
            return movieMapper.entityToDto(addMovie(newMovie));
        }

        if (imdbId != null && !imdbId.isBlank()) {
            checkIfImdbIdAlreadyExists(imdbId);
            Movie newMovie = getOmdbMovieByImdbId(imdbId);
            return movieMapper.entityToDto(addMovie(newMovie));

        } else if (title != null && !title.isBlank()) {
            title = title.replace("\"", "");
            checkIfTitleAlreadyExists(title);
            Movie newMovie = getOmdbMovieByTitle(title);
            checkIfTitleAlreadyExists(newMovie.getTitle());
            return movieMapper.entityToDto(addMovie(newMovie));
        }

        throw new IllegalArgumentException("No inputs given");
    }

    public MovieResponseDto replaceMovie(Long id, MovieRequestDto moviedto){
        Movie newMovie = movieMapper.dtoToEntity(moviedto);
        Movie refMovie = movieRepository.getReferenceById(id);
        newMovie.setId(refMovie.getId());
        return movieMapper.entityToDto(movieRepository.save(newMovie));
    }

    public void deleteMovie(Long id){
        Movie movie = movieRepository.getReferenceById(id);
        if(screeningRepository.existsByMovie(movie)){
            throw new ResourceHasChildrenException("Movie can't be deleted because a screening is referencing it");
        }
        movieRepository.delete(movie);
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

        return movieMapper.omdbMovieResponseToEntity(omdbMovieResponse);
    }

    public void checkIfTitleAlreadyExists(String title){
        if (movieRepository.existsByTitleIgnoreCase(title)){
            throw new ResourceAlreadyExistsException("Movie already exists");
        }
    }

    public void checkIfImdbIdAlreadyExists(String imdbId){
        if (movieRepository.existsByImdbIdIgnoreCase(imdbId)){
            throw new ResourceAlreadyExistsException("Movie already exists");
        }
    }


    public MovieResponseDto archive(Long id) {
        //TODO: API endpoint for AI to archive movie
        Movie movie = movieRepository.findById(id)
                                     .orElseThrow(() -> new ResourceNotFoundException("Movie id not found."));
        if (movie.getMovieStatus() != MovieStatus.ARCHIVED){
            movie.setMovieStatus(MovieStatus.ARCHIVED);
            return movieMapper.entityToDto(movieRepository.save(movie));
        }
        return movieMapper.entityToDto(movie);
    }

    public MovieResponseDto catalog(Long id) {
        Movie movie = movieRepository.findById(id)
                                     .orElseThrow(() -> new ResourceNotFoundException("Movie id not found."));
        if (movie.getMovieStatus() != MovieStatus.IN_CATALOG){
            movie.setMovieStatus(MovieStatus.IN_CATALOG);
            return movieMapper.entityToDto(movieRepository.save(movie));
        }
        return movieMapper.entityToDto(movie);
    }
}
