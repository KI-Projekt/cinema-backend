package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitle(String title);

    Optional<Movie> findByImdbId(String imdbId);

    @Query("FROM Movie m WHERE UPPER(m.title) LIKE CONCAT('%', UPPER(?1), '%')")
    List<Movie> findAllByTitle(String title);

    @Query("FROM Movie m WHERE UPPER(m.genre) LIKE CONCAT('%', UPPER(?1), '%')")
    List<Movie> findAllByGenre(String genre);

    @Query("FROM Movie m WHERE UPPER(m.actors) LIKE CONCAT('%', UPPER(?1), '%')")
    List<Movie> findAllByActor(String actor);

    List<Movie> findAllByReleaseYear(String year);
}
