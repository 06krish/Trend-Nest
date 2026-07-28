package com.trendnest.trendnest_backend.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message){
        super(message); // it calls the parent class constructor.
    }
}
