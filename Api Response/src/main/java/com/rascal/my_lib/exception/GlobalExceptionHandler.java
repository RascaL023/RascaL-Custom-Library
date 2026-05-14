package com.rascal.my_lib.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rascal.my_lib.util.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(
        ConflictException ex
    ) {
        return ApiResponse.error(
            HttpStatus.CONFLICT, 
            409, "Conflict", 
            ex.getMessage()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound (
        NotFoundException ex
    ) {
        return ApiResponse.error(
            HttpStatus.NOT_FOUND, 
            404, "Not Found", 
            ex.getMessage()
        );
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest (
        BadRequestException ex
    ) {
        return ApiResponse.error(
            HttpStatus.BAD_REQUEST, 
            401, "Bad Request", 
            ex.getMessage()
        );
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation (
        MethodArgumentNotValidException ex
    ) {
        var errorDetails = ex.getBindingResult().getFieldErrors()
            .stream().map(field -> Map.of(
                "field", field.getField(),
                "message", field.getDefaultMessage()
            )).toList();

        return ResponseEntity.badRequest().body(
            Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "error", "Validation failed",
                "errorDetail", errorDetails
            )
        );
    }

    // ===== BUILT-IN SPRING / JPA EXCEPTIONS =====

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, 400, "Invalid Argument", ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, 400, "Malformed JSON", ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return ApiResponse.error(HttpStatus.METHOD_NOT_ALLOWED, 405, "Method Not Allowed", ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedMedia(HttpMediaTypeNotSupportedException ex) {
        return ApiResponse.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 415, "Unsupported Media Type", ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParam(MissingServletRequestParameterException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, 400, "Missing Parameter", ex.getMessage());
    }


    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<Map<String, Object>> handleOther(Exception e) {
        return ApiResponse.error(
            HttpStatus.INTERNAL_SERVER_ERROR, 
            500, "Internal Server Error", 
            e.getMessage()
        );
    }

}
