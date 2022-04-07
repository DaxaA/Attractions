package com.spring.daxa.repositories;

import com.spring.daxa.entity.Attraction;
import com.spring.daxa.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository <Review, Long> {
    List<Review> getReviewsByAttraction(Attraction attraction);
}
