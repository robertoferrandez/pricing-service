package com.pricing.services.exceptions;

public class PriceNotFoundException extends RuntimeException {

    // Constructor con mensaje
    public PriceNotFoundException(String message) {
        super(message);
    }

    // Constructor con mensaje y causa
    public PriceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}