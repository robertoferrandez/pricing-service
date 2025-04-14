package com.pricing.services.exceptions.custom;

public class InvalidLoginException extends RuntimeException {


    public InvalidLoginException(String message) {
        super(message);
    }
}