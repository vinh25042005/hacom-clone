package com.hacom.clone.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path root = Paths.get("uploads");

    public String save(MultipartFile file) {
        try {
            // Tạo thư mục nếu chưa có
            if (!Files.exists(root)) Files.createDirectory(root);
            
            // Đổi tên file thành duy nhất để không bị trùng (dùng UUID)
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
            
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Không thể lưu file ảnh: " + e.getMessage());
        }
    }
}