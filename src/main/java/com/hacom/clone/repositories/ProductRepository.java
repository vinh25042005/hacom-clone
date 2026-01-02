package com.hacom.clone.repositories;

import com.hacom.clone.entities.Category;
import com.hacom.clone.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Category findByName(String name);
    // Không cần viết gì thêm, JpaRepository đã có sẵn các hàm Save, FindAll, Delete...
}