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

    // Lấy tất cả danh mục
    public List<Category> getAll() { 
        return categoryRepository.findAll(); 
    }
    
    // Lưu danh mục mới với kiểm tra trùng tên
    public Category save(Category category) { 
        Category existingCategory = categoryRepository.findByName(category.getName());
        
        if (existingCategory != null) {
            throw new RuntimeException("Tên danh mục '" + category.getName() + "' này đã tồn tại rồi!");
        }
        
        return categoryRepository.save(category);
    }
    
    // Lấy chi tiết danh mục - Đã sửa từ orElse(null) sang orElseThrow
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục có ID: " + id));
    }

    // Xóa danh mục
    public void deleteById(Long id) {
        // 1. Kiểm tra xem danh mục có tồn tại không trước khi xóa
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Không thể xóa! Không tìm thấy danh mục với ID: " + id);
        }
        
        // 2. Thực hiện xóa
        // Lưu ý: Nếu có sản phẩm thuộc danh mục này, MySQL sẽ báo lỗi ràng buộc khóa ngoại
        categoryRepository.deleteById(id);
    }
}