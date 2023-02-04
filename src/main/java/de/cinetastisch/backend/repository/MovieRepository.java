package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Movie;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    Boolean existsByTitleIgnoreCase(String title);
    Boolean existsByImdbIdIgnoreCase(String imdbId);

    @Override
    List<Movie> findAll(@Nullable Specification spec, Sort sort);
}
