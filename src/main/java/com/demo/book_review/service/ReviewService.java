package com.demo.book_review.service;

import com.demo.book_review.entity.Review;
import com.demo.book_review.entity.User;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getAllReviews(String title, boolean highRated);

    List<Review> getUserReviews(Long userId);

    Review addReview(Review review, User user);

    Review updateReview(Long reviewId, Review updatedReview, User user);

    void deleteReview(Long reviewId, User user);

    Optional<Review> getReviewById(Long id);
}
