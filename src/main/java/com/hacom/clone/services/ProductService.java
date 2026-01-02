package com.hacom.clone.services;

import com.hacom.clone.entities.Category;
import com.hacom.clone.entities.Product;
import com.hacom.clone.repositories.CategoryRepository;
import com.hacom.clone.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    // Tiêm (Inject) Repository vào để sử dụng
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Hàm lấy danh sách sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Hàm thêm sản phẩm mới (Có kiểm tra nghiệp vụ)
    public Product saveProduct(Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
        // Tìm category từ DB dựa trên ID gửi lên
        Category cat = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category không tồn tại!"));
        product.setCategory(cat);
    }
        // Ví dụ logic nghiệp vụ: Không cho phép lưu sản phẩm giá âm
        if (product.getPrice() < 0) {
            throw new RuntimeException("Giá sản phẩm không được nhỏ hơn 0");
        }
        return productRepository.save(product);
    }
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm id: " + id));
        
        product.setName(productDetails.getName());
        product.setBrand(productDetails.getBrand());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        
        // Nếu có cập nhật cả danh mục
        if (productDetails.getCategory() != null) {
            product.setCategory(productDetails.getCategory());
        }

        return productRepository.save(product);
    }

    // Thêm vào trong ProductService.java
public List<Product> searchByName(String name) {
    return productRepository.findByNameContainingIgnoreCase(name);
}

public List<Product> filterByPrice(Double min, Double max) {
    return productRepository.findByPriceBetween(min, max);
}
}