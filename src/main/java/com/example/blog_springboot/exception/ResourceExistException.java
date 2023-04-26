package com.example.blog_springboot.exception;

public class ResourceExistException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public ResourceExistException(String message) {
        super(message);
    }
}
