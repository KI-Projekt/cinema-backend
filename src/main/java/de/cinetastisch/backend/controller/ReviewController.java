package de.cinetastisch.backend.controller;


import de.cinetastisch.backend.dto.request.ReviewRequestDto;
import de.cinetastisch.backend.model.Review;
import de.cinetastisch.backend.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody ReviewRequestDto request){
        return new ResponseEntity<>(reviewService.addReview(request), HttpStatus.CREATED);
    }
}
