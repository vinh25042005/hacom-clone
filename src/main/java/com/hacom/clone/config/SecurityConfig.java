package com.hacom.clone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Dùng để mã hóa mật khẩu
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Tắt CSRF để làm việc với API dễ hơn
            .authorizeHttpRequests(auth -> auth
                // 1. Ai cũng có thể xem sản phẩm và danh mục
                .requestMatchers(HttpMethod.GET, "/api/products/**", "/api/categories/**", "/uploads/**").permitAll()
                
                // 2. Chỉ Admin mới được thêm/sửa/xóa sản phẩm và danh mục
                .requestMatchers(HttpMethod.POST, "/api/products/**", "/api/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                
                // 3. Chỉ khách đã đăng nhập mới được đặt hàng
                .requestMatchers(HttpMethod.POST, "/api/orders/**").hasRole("CUSTOMER")
                
                // 4. Các link đăng nhập/đăng ký cho phép tất cả
                .requestMatchers("/api/auth/**").permitAll()
                
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}