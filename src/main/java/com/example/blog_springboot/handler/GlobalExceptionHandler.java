package com.example.blog_springboot.handler;

import com.example.blog_springboot.exception.ResourceExistException;
import com.example.blog_springboot.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException err) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("errors", err.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    @ExceptionHandler(ResourceExistException.class)
    public ResponseEntity<Object> handleResourceExistException(ResourceExistException err) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status",HttpStatus.NOT_FOUND.value());
        body.put("errors", err.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
