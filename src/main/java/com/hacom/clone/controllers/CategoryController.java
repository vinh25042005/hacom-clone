package com.hacom.clone.controllers;

import com.hacom.clone.entities.Category;
import com.hacom.clone.services.CategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAll() { return categoryService.getAll(); }

    @PostMapping
    
    public Category create(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @DeleteMapping("/{id}")
public String deleteCategory(@PathVariable Long id) {
    categoryService.deleteById(id); // Bạn hãy tự tạo hàm deleteById trong Service nhé
    return "Đã xóa danh mục có ID: " + id;
}
}