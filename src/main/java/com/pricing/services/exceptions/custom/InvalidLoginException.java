package com.pricing.services.exceptions.custom;

public class InvalidLoginException extends RuntimeException {

    // Constructor con mensaje
    public InvalidLoginException(String message) {
        super(message);
    }
}