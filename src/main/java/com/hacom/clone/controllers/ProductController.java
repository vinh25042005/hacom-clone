package com.hacom.clone.controllers;

import com.hacom.clone.entities.Product;
import com.hacom.clone.services.ProductService; // Đổi sang gọi Service
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // Inject Service vào Controller
    public ProductController(ProductService productService) {
        this.productService = productService;
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