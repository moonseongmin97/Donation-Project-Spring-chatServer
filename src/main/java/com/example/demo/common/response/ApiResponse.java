package com.example.demo.common.response;

public class ApiResponse {
    private boolean success;
    private String message;

    // 모든 필드를 포함한 생성자
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getter와 Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
