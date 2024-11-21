package com.demo.book_review.service;

import com.demo.book_review.entity.Review;
import com.demo.book_review.entity.User;
import com.demo.book_review.repository.ReviewRepository;
import com.demo.book_review.repository.UserRepository;
import com.demo.book_review.service.implement.ReviewServiceImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewServiceImplementTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewServiceImplement reviewService;

    private Review review;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("TestUser");

        review = new Review();
        review.setId(1L);
        review.setTitle("Test Title");
        review.setAuthor("Test Author");
        review.setRating(5);
        review.setReviewText("This is a great book.");
        review.setUser(user);
    }

    @Test
    void testAddReview() {
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review savedReview = reviewService.addReview(review, user);

        assertNotNull(savedReview);
        assertEquals("Test Title", savedReview.getTitle());
        assertEquals(user, savedReview.getUser());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testUpdateReview() {
        Review updatedReview = new Review();
        updatedReview.setTitle("Updated Title");
        updatedReview.setAuthor("Updated Author");
        updatedReview.setRating(4);
        updatedReview.setReviewText("Updated review text.");

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);

        Review result = reviewService.updateReview(1L, updatedReview, user);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        assertEquals(4, result.getRating());
        assertEquals("Updated review text.", result.getReviewText());
        verify(reviewRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testUpdateReview_unauthorizedUser() {
        User otherUser = new User();
        otherUser.setId(2L);

        Review updatedReview = new Review();
        updatedReview.setTitle("Updated Title");
        updatedReview.setAuthor("Updated Author");
        updatedReview.setRating(4);
        updatedReview.setReviewText("Updated review text.");

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        assertThrows(RuntimeException.class, () -> reviewService.updateReview(1L, updatedReview, otherUser));
    }

    @Test
    void testDeleteReview() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        doNothing().when(reviewRepository).delete(any(Review.class));

        reviewService.deleteReview(1L, user);

        verify(reviewRepository, times(1)).delete(any(Review.class));
    }

    @Test
    void testDeleteReview_unauthorizedUser() {
        User otherUser = new User();
        otherUser.setId(2L);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        assertThrows(RuntimeException.class, () -> reviewService.deleteReview(1L, otherUser));
    }

    @Test
    void testGetReviewById() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Optional<Review> result = reviewService.getReviewById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testGetReviewById_notFound() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Review> result = reviewService.getReviewById(1L);

        assertFalse(result.isPresent());
    }

}
