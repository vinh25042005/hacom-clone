package com.hacom.clone.controllers;

import com.hacom.clone.entities.Review;
import com.hacom.clone.services.ReviewService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Gửi đánh giá cho một sản phẩm
    @PostMapping("/product/{productId}")
    public Review createReview(@PathVariable Long productId, @RequestBody Review review) {
        return reviewService.addReview(productId, review);
    }

    // Lấy tất cả đánh giá của một sản phẩm
    @GetMapping("/product/{productId}")
    public List<Review> getReviews(@PathVariable Long productId) {
        return reviewService.getReviewsByProduct(productId);
    }
}