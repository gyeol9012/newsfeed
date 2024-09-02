package com.sparta.newsfeed19.global.exception;

import com.sparta.newsfeed19.global.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException apiException) {
        ApiResponse apiResponse = new ApiResponse(apiException.getCode().value(), apiException.getMessage(), null);
        return new ResponseEntity<>(apiResponse, apiException.getCode());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponse apiResponse = new ApiResponse(code.value(), exception.getMessage(), null);
        return new ResponseEntity<>(apiResponse, code);
    }
}
