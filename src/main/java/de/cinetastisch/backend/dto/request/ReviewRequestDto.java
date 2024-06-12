package de.cinetastisch.backend.dto.request;


import lombok.Data;

@Data
public class ReviewRequestDto {

    private Long userId;
    private Long movieId;
    private String tags; // Semicolon separated
    private double rating;
}
