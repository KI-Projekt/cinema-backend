package de.cinetastisch.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.cinetastisch.backend.dto.request.AiMovieRequestDTO;
import de.cinetastisch.backend.dto.request.AiRatingRequestDTO;
import de.cinetastisch.backend.dto.request.AiScoreRequestDTO;
import de.cinetastisch.backend.dto.response.AiScoreMoviesResponseDTO;
import de.cinetastisch.backend.dto.response.AiScoreResponseDTO;
import de.cinetastisch.backend.enumeration.MovieStatus;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.MovieMapper;
import de.cinetastisch.backend.model.FavoriteGenres;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Review;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.FavoriteGenreRepository;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.ReviewRepository;
import de.cinetastisch.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

@Service
public class AiScoreService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FavoriteGenreRepository favoriteGenreRepository;

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    MovieMapper movieMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String AiScoreServiceUrl = "https://moviemate.mabu2807.de/api/data";



    public ResponseEntity<?> getAiScore(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<FavoriteGenres> favoriteGenres = favoriteGenreRepository.findByUser(user);

        ArrayList<String> genres = new ArrayList<>();
        for (FavoriteGenres favoriteGenre : favoriteGenres){
            genres.add(favoriteGenre.getGenre().name());
        }
        List<Review> reviews = reviewRepository.findByUser(user);
        List<AiRatingRequestDTO> ratings = new ArrayList<>();
        for (Review review : reviews){
            AiRatingRequestDTO ratingDTO = new AiRatingRequestDTO(review.getMovie().getId(), review.getMovie().getTitle(), Integer.parseInt(review.getMovie().getReleaseYear()), review.getRating());
            ratings.add(ratingDTO);
        }

        List<Movie> movies = movieRepository.findAllByMovieStatus(MovieStatus.IN_CATALOG);

        List<AiMovieRequestDTO> aiMovies = movieMapper.entityToAiMovieDto(movies);

        AiScoreRequestDTO aiScoreRequestDTO = new AiScoreRequestDTO(aiMovies, ratings);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AiScoreRequestDTO> request = new HttpEntity<>(aiScoreRequestDTO, headers);
        System.out.println(aiScoreRequestDTO);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(AiScoreServiceUrl, HttpMethod.POST, request, String.class);
            AiScoreMoviesResponseDTO aiScoreResponseDTO = objectMapper.readValue(responseEntity.getBody(), new TypeReference<AiScoreMoviesResponseDTO>() {
                }); // This line is throwing an exception
            System.out.println(aiScoreResponseDTO);

            return ResponseEntity.ok(aiScoreResponseDTO.getMovies());
        } catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.noContent().build();

    }
}
