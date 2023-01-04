package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitleIgnoreCase(String title);
    Optional<Movie> findByImdbIdIgnoreCase(String imdbId);

    List<Movie> findAllByTitleLikeIgnoreCase(String formattedTitle);
    List<Movie> findAllByImdbIdLikeIgnoreCase(String formattedImdbId);
    List<Movie> findAllByGenreLikeIgnoreCase(String formattedGenre);
    List<Movie> findAllByActorsIgnoreCase(String actor);
    List<Movie> findAllByReleaseYear(String year);

    Boolean existsByTitleIgnoreCase(String title);
    Boolean existsByImdbIdIgnoreCase(String imdbId);

}
