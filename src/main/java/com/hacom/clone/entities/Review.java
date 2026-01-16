package com.hacom.clone.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName; // Tên người bình luận
    private String content;      // Nội dung đánh giá
    private Integer rating;      // Số sao (1 đến 5)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Đánh giá này thuộc về sản phẩm nào
}