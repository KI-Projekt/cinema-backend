package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.FavoriteGenres;
import de.cinetastisch.backend.service.FavoriteGerneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorite-genres")
public class FavoriteGenreController {

    private final FavoriteGerneService favoriteGerneService;

    public FavoriteGenreController(FavoriteGerneService favoriteGerneService) {
        this.favoriteGerneService = favoriteGerneService;
    }


    @PutMapping("/{userId}/{genre}")
    public ResponseEntity<?> addFavoriteGenre(@PathVariable Long userId, @PathVariable String genre){
        favoriteGerneService.addFavoriteGenre(userId, genre);
        return ResponseEntity.ok().build();
    }
}
