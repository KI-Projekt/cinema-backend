package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findByTitle(String title);

    Movie findByImdbId(String imdbId);

    @Query("SELECT m FROM Movie m WHERE UPPER(m.genre) LIKE CONCAT('%', UPPER(?1), '%')")
    List<Movie> findAllByGenre(String genre);

    @Query("SELECT m FROM Movie m WHERE UPPER(m.actors) LIKE CONCAT('%', UPPER(?1), '%')")
    List<Movie> findAllByActor(String actor);

    List<Movie> findAllByReleaseYear(String year);
}
