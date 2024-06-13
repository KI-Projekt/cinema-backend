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

        for (String g : splitStringToGenres(genre)){
            Genre genre1 = Genre.valueOf(g);
            favoriteGenreRepository.save(new FavoriteGenres(genre1, user));
        }


    }

    public ArrayList<String> splitStringToGenres(String genre){
        String[] genres = genre.split(",");
        ArrayList<String> genreList = new ArrayList<>();
        for (String g : genres){
            genreList.add(g);
        }
        return genreList;
    }
}
