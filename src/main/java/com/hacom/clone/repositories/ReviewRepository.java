package com.hacom.clone.repositories;

import com.hacom.clone.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Tìm tất cả đánh giá của một sản phẩm dựa vào ID sản phẩm
    List<Review> findByProductId(Long productId);
}