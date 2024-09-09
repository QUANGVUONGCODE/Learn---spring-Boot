package com.api.exception;

public enum ErrorCode {
    DELETE_ID(1004, "Delete user with id"),
    FOUND_ID(1003, "Not Found user with id"),
    USER_EXISTS(1000, "User already exists"),
    USERNAME_ERROR(1001, "Username must be at least 5 characters long"),
    PASSWORD_ERROR(1002, "Password must be at least 8 characters long"),
    USERNAME_NOT_FOUND(1005, "Not Found User with Name"),
    UNAUTHENTICATED(1006, "Unthenticated");
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
