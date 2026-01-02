package com.hacom.clone.exception;

import java.time.LocalDateTime;

public class ErrorMessage {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorMessage(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Các Getter để Spring có thể đọc dữ liệu và chuyển thành JSON
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}