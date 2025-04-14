package com.pricing.services.exceptions.custom;

public class PriceNotFoundException extends RuntimeException {


    public PriceNotFoundException(String message) {
        super(message);
    }
}