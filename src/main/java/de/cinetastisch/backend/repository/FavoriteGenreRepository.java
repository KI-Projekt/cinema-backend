package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.enumeration.Genre;
import de.cinetastisch.backend.model.FavoriteGenres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteGenreRepository extends JpaRepository<FavoriteGenres, Long> {
        boolean existsByGenreAndUser_Id(Genre genre, Long userId);
        FavoriteGenres findByGenreAndUser_Id(Genre genre, Long userId);
}
