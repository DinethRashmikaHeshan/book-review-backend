package com.demo.book_review.service.implement;

import com.demo.book_review.entity.Review;
import com.demo.book_review.entity.User;
import com.demo.book_review.repository.ReviewRepository;
import com.demo.book_review.repository.UserRepository;
import com.demo.book_review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImplement implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Review> getAllReviews(String title, boolean highRated) {
        if (title != null) {
            return reviewRepository.findByTitleContainingIgnoreCase(title);
        }
        if (highRated) {
            return reviewRepository.findTop20ByOrderByRatingDesc();
        }
        return reviewRepository.findAll();
    }

    public List<Review> getUserReviews(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Review addReview(Review review, User user) {
        review.setUser(user);
        return reviewRepository.save(review);
    }

    public Review updateReview(Long reviewId, Review updatedReview, User user) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!existingReview.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to update this review");
        }

        existingReview.setTitle(updatedReview.getTitle());
        existingReview.setAuthor(updatedReview.getAuthor());
        existingReview.setRating(updatedReview.getRating());
        existingReview.setReviewText(updatedReview.getReviewText());
        return reviewRepository.save(existingReview);
    }

    public void deleteReview(Long reviewId, User user) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!existingReview.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to delete this review");
        }

        reviewRepository.delete(existingReview);
    }
}

