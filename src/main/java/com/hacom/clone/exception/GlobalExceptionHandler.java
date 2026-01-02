package com.hacom.clone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Đánh dấu đây là class xử lý lỗi chung cho toàn bộ Controller
public class GlobalExceptionHandler {

    // 1. Xử lý lỗi "Không tìm thấy" (404 Not Found)
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFound(RuntimeException ex) {
        // Trả về object ErrorMessage thay vì một đống text lỗi loằng ngoằng
        return new ErrorMessage(404, ex.getMessage());
    }

    // 2. Xử lý tất cả các lỗi khác (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleGeneralException(Exception ex) {
        return new ErrorMessage(500, "Có lỗi hệ thống xảy ra, vui lòng thử lại sau!");
    }
}