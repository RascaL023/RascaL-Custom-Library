package id.rascal.response_kit.util;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import id.rascal.response_kit.template.*;

public class ApiResponse {

    public static ResponseEntity<?> error (
        HttpStatus httpStatus,
        int status,
        String errorType,
        String message
    ) {
        return ResponseEntity.status(httpStatus).body(
            new ErrorTemplate(
                httpStatus,
                status,
                errorType,
                message,
                LocalDateTime.now()
            )
        );
    }

    public static ResponseEntity<?> success (
        HttpStatus httpStatus,
        Object data
    ) {
        return ResponseEntity.status(httpStatus).body(
            new SuccessTemplate(data)
        );
    }

    public static ResponseEntity<?> paged (
        HttpStatus httpStatus,
        Page<?> page
    ) {
        MetaTemplate meta = new MetaTemplate(
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.hasNext(),
            page.hasPrevious()
        );

        return ResponseEntity.status(httpStatus).body(
            new SuccessPagedTemplate(
                new SuccessTemplate(page.getContent()), 
                meta
            )
        );
    }

}
