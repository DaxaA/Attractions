package com.spring.daxa.services;

import com.spring.daxa.entity.Attraction;
import com.spring.daxa.entity.Review;
import com.spring.daxa.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewByAttraction(Attraction attraction) {
        return reviewRepository.getReviewsByAttraction(attraction);
    }
}
