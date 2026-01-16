package com.hacom.clone.controllers;

import com.hacom.clone.entities.Product;
import com.hacom.clone.services.FileStorageService;
import com.hacom.clone.services.ProductService; // Đổi sang gọi Service
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    private final FileStorageService fileStorageService;
    // Inject Service vào Controller
    public ProductController(ProductService productService, FileStorageService fileStorageService) {
        this.productService = productService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload-image/{id}")
    public Product uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.save(file);
        Product product = productService.getProductById(id);
        product.setImageUrl(fileName);
        return productService.saveProduct(product); // Lưu lại sản phẩm đã có ảnh
    }
    
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

   @GetMapping("/search")
    public List<Product> search(@RequestParam String name) {
        return productService.searchByName(name); 
    }

    @GetMapping("/filter")
    public List<Product> filterByPrice(@RequestParam Double min, @RequestParam Double max) {
        return productService.filterByPrice(min, max);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getByCategory(@PathVariable Long categoryId, @RequestParam(defaultValue = "asc") String direction) {
        return productService.getProductsByCategorySorted(categoryId, direction);
    }

    @GetMapping("/sorted")
    public List<Product> getProductsSorted(@RequestParam(defaultValue = "asc") String direction) {
        return productService.getAllProductsSorted(direction);
    }
}