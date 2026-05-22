package id.rascal.response_kit.template;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public record ErrorTemplate(
    HttpStatus httpStatus,
    int status,
    String errorType,
    String message,
    LocalDateTime timestamp
) { }

