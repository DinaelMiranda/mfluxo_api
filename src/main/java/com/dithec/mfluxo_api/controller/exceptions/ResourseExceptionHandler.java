package com.dithec.mfluxo_api.controller.exceptions;

import com.dithec.mfluxo_api.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice // Anotação que intercepta as excessões que acontecerem
public class ResourseExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) // Intercepta excessões do tipo ResourceNotFoundException
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        String error = "Resource Not Found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }
}
