package com.acm.helper;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super();
    }

    public ResourceNotFoundException(){
        super("Resource not found...");
    }
}
