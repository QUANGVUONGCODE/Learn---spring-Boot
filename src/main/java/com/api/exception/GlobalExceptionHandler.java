package com.api.exception;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.dbo.request.ApiRequest;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";

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
        ErrorCode errorCode = ErrorCode.DOB_INVALID;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumkey);
            var constrainViolation = e.getBindingResult()
                    .getAllErrors().get(0).unwrap(ConstraintViolation.class);

            attributes = constrainViolation.getConstraintDescriptor().getAttributes();
            log.info(attributes.toString());
        } catch (IllegalArgumentException exception) {
            // TODO: handle exception
        }
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setCode(errorCode.getCode());
        apiRequest.setMessage(Objects.nonNull(attributes) ? mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRequest);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
