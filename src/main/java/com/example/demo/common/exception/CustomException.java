package com.example.demo.common.exception;

public class CustomException extends RuntimeException {
    private String errorCode; // 예외에 대한 고유 코드 (옵션)
    
    // 기본 생성자
    public CustomException(String message) {
    	
        super(message);
    }

    // 원인 예외와 메시지를 받는 생성자
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    // 메시지와 에러 코드를 함께 받는 생성자
    public CustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    // 메시지, 에러 코드, 원인 예외를 받는 생성자
    public CustomException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    // 에러 코드 Getter
    public String getErrorCode() {
        return errorCode;
    }
}
