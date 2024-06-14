package de.cinetastisch.backend.service;

import de.cinetastisch.backend.enumeration.Genre;
import de.cinetastisch.backend.model.FavoriteGenres;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.FavoriteGenreRepository;
import de.cinetastisch.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FavoriteGerneService {

    @Autowired
    private FavoriteGenreRepository favoriteGenreRepository;

    @Autowired
    private UserRepository userRepository;

    public void addFavoriteGenre(Long userId, String genre){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Genre genreEnum = Genre.valueOf(genre);

        if(favoriteGenreRepository.existsByGenreAndUser_Id(genreEnum, userId)){
            FavoriteGenres favoriteGenres = favoriteGenreRepository.findByGenreAndUser_Id(genreEnum, userId);
            favoriteGenreRepository.delete(favoriteGenres);
        }else{
            FavoriteGenres favoriteGenres = new FavoriteGenres(genreEnum, user);
            favoriteGenreRepository.save(favoriteGenres);
        }



    }

}
