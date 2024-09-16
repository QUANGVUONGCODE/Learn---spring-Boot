package com.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.dbo.request.ApiRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiRequest> handlingRuntimeException(RuntimeException e) {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setCode(ErrorCode.USER_EXISTS.getCode());
        apiRequest.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(apiRequest);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiRequest> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setCode(errorCode.getCode());
        apiRequest.setMessage(errorCode.getMessage());
        if (apiRequest.getCode() == 1004) {
            return ResponseEntity.ok().body(apiRequest);
        }
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(apiRequest);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiRequest> handlingAccessDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                ApiRequest.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiRequest> handlingValidation(MethodArgumentNotValidException e) {
        String enumkey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumkey);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setCode(errorCode.getCode());
        apiRequest.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRequest);
    }
}
