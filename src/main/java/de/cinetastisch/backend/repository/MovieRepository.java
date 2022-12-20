package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
