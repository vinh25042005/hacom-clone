package com.hacom.clone.repositories;

import com.hacom.clone.entities.Category;
import com.hacom.clone.entities.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Category findByName(String name);
    // Không cần viết gì thêm, JpaRepository đã có sẵn các hàm Save, FindAll, Delete...
    List<Product> findByNameContainingIgnoreCase(String name);
    
    // Tìm theo hãng
    List<Product> findByBrand(String brand);
    
    // Tìm sản phẩm trong khoảng giá
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}