package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.FavoriteGenres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteGenreRepository extends JpaRepository<FavoriteGenres, Long> {

}
