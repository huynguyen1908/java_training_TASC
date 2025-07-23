package org.example.exception;

import org.example.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Map<String, Object>> handleAppException(AppException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("code", ex.getCode());
        error.put("field", ex.getField());
        error.put("message", ex.getMessage());

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(error);
    }

//    @ExceptionHandler(AppException.class)
//    public ResponseEntity<?>handleAppException(AppException appException){
//        return ResponseEntity.badRequest()
//                .body(ApiResponse.builder().code(appException.getCode()).field(appException.getField()).message(appException.getMessage()));
//    }
}

