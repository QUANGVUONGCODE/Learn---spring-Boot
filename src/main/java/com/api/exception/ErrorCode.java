package com.api.exception;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    DELETE_ID(1004, "Delete user with id", HttpStatus.BAD_REQUEST),
    FOUND_ID(1003, "Not Found user with id", HttpStatus.BAD_REQUEST),
    USER_EXISTS(1000, "User already exists", HttpStatus.NOT_FOUND),
    USERNAME_ERROR(1001, "Username must be at least 5 characters long", HttpStatus.BAD_REQUEST),
    PASSWORD_ERROR(1002, "Password must be at least 8 characters long", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_FOUND(1005, "Not Found User with Name", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unthenticated", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED(1007, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(1008, "you do not have permission", HttpStatus.FORBIDDEN),
    DOB_INVALID(1009, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    int code;
    String message;
    HttpStatusCode httpStatusCode;

}
