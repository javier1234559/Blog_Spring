package com.example.blog_springboot.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
