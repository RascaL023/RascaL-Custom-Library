package com.rascal.my_lib.util;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    public static ResponseEntity<Map<String, Object>> error (
        HttpStatus httpStatus,
        int status,
        String errorType,
        String message
    ) {
        return ResponseEntity.status(httpStatus).body(
            Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status,
                "error", errorType,
                "message", message
            )
        );
    }

    public static ResponseEntity<Map<String, Object>> success (
        HttpStatus httpStatus,
        Object data
    ) {
        return ResponseEntity.status(httpStatus).body(
            Map.of("data", data)
        );
    }

    public static ResponseEntity<Map<String,Object>> paged (
        HttpStatus httpStatus,
        Page<?> page
    ) {
        Map<String, Object> meta = Map.of(
            "page", page.getNumber(),
            "size", page.getSize(),
            "totalElements", page.getTotalElements(),
            "totalPages", page.getTotalPages(),
            "hasNext", page.hasNext(),
            "hasPrevious", page.hasPrevious()
        );

        return ResponseEntity.status(httpStatus).body(
            Map.of(
                "data", page.getContent(),
                "meta", meta
            )
        );
    }

}
