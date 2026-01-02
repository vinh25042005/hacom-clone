package com.hacom.clone.services;

import com.hacom.clone.entities.Category;
import com.hacom.clone.entities.Product;
import com.hacom.clone.repositories.CategoryRepository;
import com.hacom.clone.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy chi tiết 1 sản phẩm - Đã thêm orElseThrow để làm đẹp lỗi
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm có ID " + id + " không tồn tại!"));
    }

    // Thêm sản phẩm mới
    public Product saveProduct(Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category cat = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Danh mục (Category) không tồn tại!"));
            product.setCategory(cat);
        }
        
        if (product.getPrice() < 0) {
            throw new RuntimeException("Giá sản phẩm không được nhỏ hơn 0");
        }
        return productRepository.save(product);
    }

    // Cập nhật sản phẩm
    public Product updateProduct(Long id, Product productDetails) {
        // Tìm sản phẩm cũ, nếu không thấy sẽ ném lỗi ngay lập tức
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không thể cập nhật! Không tìm thấy sản phẩm ID: " + id));
        
        product.setName(productDetails.getName());
        product.setBrand(productDetails.getBrand());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        
        if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
            Category cat = categoryRepository.findById(productDetails.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Danh mục mới không hợp lệ!"));
            product.setCategory(cat);
        }

        return productRepository.save(product);
    }

    // Xóa sản phẩm
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không thể xóa! Sản phẩm ID " + id + " không tồn tại."));
        productRepository.delete(product);
    }

    // Tìm kiếm theo tên
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Lọc theo khoảng giá
    public List<Product> filterByPrice(Double min, Double max) {
        return productRepository.findByPriceBetween(min, max);
    }

    // Lấy tất cả và sắp xếp
    public List<Product> getAllProductsSorted(String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") 
                    ? Sort.by("price").descending() 
                    : Sort.by("price").ascending();
        return productRepository.findAll(sort);
    }

    // Lọc theo danh mục và sắp xếp
    public List<Product> getProductsByCategorySorted(Long categoryId, String direction) {
        // Kiểm tra xem danh mục có tồn tại không trước khi lọc
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Không tìm thấy danh mục có ID: " + categoryId);
        }

        Sort sort = direction.equalsIgnoreCase("desc") 
                    ? Sort.by("price").descending() 
                    : Sort.by("price").ascending();
        return productRepository.findByCategoryId(categoryId, sort);
    }
}