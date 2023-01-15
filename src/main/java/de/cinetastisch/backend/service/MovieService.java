package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.MovieRequestDto;
import de.cinetastisch.backend.dto.MovieResponseDto;
import de.cinetastisch.backend.dto.ScreeningResponseDto;
import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.enumeration.MovieStatus;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceHasChildrenException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.MovieMapper;
import de.cinetastisch.backend.mapper.ScreeningMapper;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.pojo.OmdbMovieResponse;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final ScreeningRepository screeningRepository;
    private final ScreeningMapper screeningMapper;



    // #########################
    // CRUD
    // #########################


    public List<MovieResponseDto> getAllMovies(String title, String genre, String imdbId, String rated){
        if (title != null && !title.isBlank() && genre != null && !genre.isBlank()){
            throw new IllegalArgumentException("Only one query parameter at a time supported.");
        }

        List<Movie> result = new ArrayList<>();

        if(imdbId != null && !imdbId.isBlank()){
            result.addAll(movieRepository.findAllByImdbIdLikeIgnoreCase("%" + imdbId + "%"));
        } else if(title != null && !title.isBlank()){
            result.addAll(movieRepository.findAllByTitleLikeIgnoreCase("%"+title+"%"));
        } else if (genre != null && !genre.isBlank()){
            result.addAll(movieRepository.findAllByGenreLikeIgnoreCase("%"+genre+"%"));
        } else if (rated != null){
            MovieRating movieRating = MovieRating.valueOfLabel(rated);
            result.addAll(movieRepository.findAllByRatedLessThanEqual(movieRating));
        } else {
            result.addAll(movieRepository.findAll());
        }

        return movieMapper.entityToDto(result);
//        List<MovieRequestDto>responseDto = response.stream().map(this::convertToDto).toList();
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
        if (movie != null){
            return movieMapper.entityToDto(addMovie(movieMapper.dtoToEntity(movie)));
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

    // ##############
    // Other
    // ##############

    public List<ScreeningResponseDto> getAllScreeningsByMovie(Long id) {
        Movie movie = movieRepository.getReferenceById(id);
        return screeningMapper.entityToDto(screeningRepository.findAllByMovie(movie));
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
