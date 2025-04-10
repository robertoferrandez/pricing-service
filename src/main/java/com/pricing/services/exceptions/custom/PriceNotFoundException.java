package com.pricing.services.exceptions.custom;

public class PriceNotFoundException extends RuntimeException {

    // Constructor con mensaje
    public PriceNotFoundException(String message) {
        super(message);
    }
}