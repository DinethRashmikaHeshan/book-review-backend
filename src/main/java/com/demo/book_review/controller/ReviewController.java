package com.demo.book_review.controller;

import com.demo.book_review.Utility.JwtUtil;
import com.demo.book_review.entity.Review;
import com.demo.book_review.entity.User;
import com.demo.book_review.repository.UserRepository;
import com.demo.book_review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Review> getAllReviews(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "true") boolean highRated) {
        return reviewService.getAllReviews(title, highRated);
    }

    @GetMapping("/my-reviews")
    public List<Review> getMyReviews(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reviewService.getUserReviews(user.getId());
    }

    @PostMapping
    public Review addReview(@RequestBody Review review, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reviewService.addReview(review, user);
    }

    @PutMapping("/{id}")
    public Review updateReview(
            @PathVariable Long id,
            @RequestBody Review updatedReview,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reviewService.updateReview(id, updatedReview, user);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        reviewService.deleteReview(id, user);
    }
}
