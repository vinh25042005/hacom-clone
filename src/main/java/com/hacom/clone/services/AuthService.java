package com.hacom.clone.services;

import com.hacom.clone.config.JwtTokenProvider;
import com.hacom.clone.entities.User;
import com.hacom.clone.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    // Thêm vào trong class AuthService
    public User register(User user) {
        // 1. Kiểm tra xem username đã tồn tại chưa
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }

        // 2. Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3. Mặc định tài khoản mới là CUSTOMER (Khách hàng)
        // Nếu muốn đăng ký Admin thì có thể sửa logic ở đây hoặc sửa trong DB sau
        user.setRole("ROLE_CUSTOMER");

        return userRepository.save(user);
    }
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Sai tài khoản hoặc mật khẩu!"));

        // Kiểm tra mật khẩu (So sánh mật khẩu nhập vào với mật khẩu đã mã hóa trong DB)
        if (passwordEncoder.matches(password, user.getPassword())) {
            return tokenProvider.generateToken(username);
        } else {
            throw new RuntimeException("Sai tài khoản hoặc mật khẩu!");
        }
    }
}