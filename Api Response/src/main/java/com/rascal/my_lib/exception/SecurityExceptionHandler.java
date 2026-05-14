package com.rascal.my_lib.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rascal.my_lib.dto.ErrorTemplate;
import com.rascal.my_lib.util.ApiResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
@ConditionalOnClass(AuthenticationEntryPoint.class) // ← hanya load kalau security ada
@RestControllerAdvice
public class SecurityExceptionHandler implements 
    AuthenticationEntryPoint, AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public SecurityExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
        HttpServletRequest request, 
        HttpServletResponse response,
        AuthenticationException ex
    ) throws IOException, ServletException {
        writeError(
            response, request, 
            401, HttpStatus.UNAUTHORIZED,
            "Unauthorized", ex.getMessage()    
        );
    }

    @Override
    public void handle(
        HttpServletRequest request, 
        HttpServletResponse response,
        AccessDeniedException ex
    ) throws IOException, ServletException {
        writeError(
            response, request, 
            403, HttpStatus.FORBIDDEN,
            "Forbidden", ex.getMessage()    
        );
       
    }

    private void writeError(
        HttpServletResponse response, 
        HttpServletRequest request,
        int status, HttpStatus httpStatus,
        String errorType, String message
    ) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(
            new ErrorTemplate(
                httpStatus, status, 
                errorType, 
                message, 
                LocalDateTime.now())
        ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuth(AuthenticationException ex) {
        return ApiResponse.error(HttpStatus.UNAUTHORIZED, 401, "Unauthorized", ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        return ApiResponse.error(HttpStatus.FORBIDDEN, 403, "Forbidden", ex.getMessage());
    }

}
