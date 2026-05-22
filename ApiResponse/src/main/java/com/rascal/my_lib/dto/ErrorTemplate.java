package com.rascal.my_lib.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public record ErrorTemplate(
    HttpStatus httpStatus,
    int status,
    String errorType,
    String message,
    LocalDateTime timestamp
) { }

