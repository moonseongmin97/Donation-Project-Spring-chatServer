package com.example.demo.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.common.response.ApiResponse;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
    	System.out.println("공통 이셉션 핸들러 1");
        return new ApiResponse(false, ex.getMessage(), null);
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleInvalidInputException(InvalidInputException ex) {
    	System.out.println("공통 이셉션 핸들러 2");
        return new ApiResponse(false, ex.getMessage(), null);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse handleUnauthorizedException(UnauthorizedException ex) {
    	System.out.println("공통 이셉션 핸들러 3");
        return new ApiResponse(false, ex.getMessage(), null);
    }
    
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleCustomException(CustomException ex) {
    	System.out.println("공통 이셉션 핸들러 4");
        return new ApiResponse(false, ex.getMessage(), "ㅎ2");
    }
    
}
