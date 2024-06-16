package de.cinetastisch.backend.service;


import de.cinetastisch.backend.dto.request.ReviewRequestDto;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReviewService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ReviewTagRepository reviewTagRepository;


    public Review addReview(ReviewRequestDto request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));


        Review review = new Review(movie,user, request.getRating());

        Review newReview = reviewRepository.save(review);

        ArrayList<String> tags = getTagsFromString(request.getTags());

        for (String tag : tags){
            if (tag.isEmpty()){
                continue;
            }
            if (!tagRepository.existsByTag(tag)){
                tagRepository.save(new Tag(tag));
            }
            reviewTagRepository.save(new ReviewTag(tagRepository.findByTag(tag),newReview));
        }

        return newReview;
    }

    public ArrayList<String> getTagsFromString(String tags){
        ArrayList<String> tagList = new ArrayList<>();
        String[] tagArray = tags.split(";");
        for (String tag : tagArray){
            tagList.add(tag);
        }
        return tagList;
    }

}
