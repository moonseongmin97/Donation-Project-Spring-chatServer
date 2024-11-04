package com.example.demo.common.response;

public class ApiResponse {
    private String success;
    private String message;

    // 모든 필드를 포함한 생성자
    public ApiResponse(String success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getter와 Setter
    public String isSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
