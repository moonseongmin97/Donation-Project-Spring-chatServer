package com.example.demo.common.response;

public class ApiResponse {
    private boolean success;
    private String message;
    private Object result;

    // 성공 후 데이터가 포함된 생성자
    public ApiResponse(boolean success, String message, Object result) {
        this.success = success;
        this.message = message;
        this.result = result;
    } 
    
    // 실패한 데이터가 없는 생성자
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getter와 Setter

	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
    public boolean getSuccess() {
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
