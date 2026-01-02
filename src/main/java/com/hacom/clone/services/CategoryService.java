package com.hacom.clone.services;

import com.hacom.clone.entities.Category;
import com.hacom.clone.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() { return categoryRepository.findAll(); }
    
    public Category save(Category category) { Category existingCategory = categoryRepository.findByName(category.getName());
    
    if (existingCategory != null) {
        throw new RuntimeException("Tên danh mục này đã tồn tại rồi!");
    }
    
    return categoryRepository.save(category);}
    
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }


    public void deleteById(Long id) {
        // 1. Kiểm tra xem danh mục có tồn tại không trước khi xóa
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy danh mục với ID: " + id);
        }
        
        // 2. Thực hiện xóa
        categoryRepository.deleteById(id);
    }
}