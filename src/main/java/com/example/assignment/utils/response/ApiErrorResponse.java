package com.example.assignment.utils.response;

public class ApiErrorResponse {
    private boolean success;
    private String message;

    public ApiErrorResponse(
            boolean success,
            String message
    ) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
