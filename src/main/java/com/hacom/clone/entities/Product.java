package com.hacom.clone.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @ManyToOne
    @JoinColumn(name = "category_id") // Tên cột khóa ngoại trong database
    private Category category;
    
    private String name;
    private Double price;
    private String brand;
    private Integer stockQuantity;
    private String imageUrl;
}