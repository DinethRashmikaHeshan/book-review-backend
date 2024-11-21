package com.demo.book_review.repository;

import com.demo.book_review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByUserId(Long userId);

    List<Review> findByTitleContainingIgnoreCase(String title);

    List<Review> findTop20ByOrderByRatingDesc();
}

