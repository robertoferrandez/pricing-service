package com.pricing.services.exceptions;

import com.pricing.services.exceptions.custom.PriceNotFoundException;
import com.pricing.services.exceptions.errors.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandlePriceNotFoundException() {
        // Given
        PriceNotFoundException ex = new PriceNotFoundException("Price not found for the given parameters.");

        // When
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handlePriceNotFoundException(ex);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("PRICE_NOT_FOUND", responseEntity.getBody().getErrorCode());
        assertEquals("Price not found for the given parameters.", responseEntity.getBody().getMessage());
    }

    @Test
    void testHandleMissingParams() {
        // Given
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("date", "String");

        // When
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleMissingParams(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("400", responseEntity.getBody().getErrorCode());
        assertTrue(responseEntity.getBody().getMessage().contains("Required request parameter 'date' is missing"));
        assertNotNull(responseEntity.getBody().getTimestamp());  // Verifica que el timestamp no sea nulo
    }

    @Test
    void testHandleConstraintViolationException() {
        // Given
        ConstraintViolationException ex = new ConstraintViolationException("Invalid parameter", null);

        // When
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleWrongParameters(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("400", responseEntity.getBody().getErrorCode());
        assertEquals("Invalid parameter", responseEntity.getBody().getMessage());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }

    @Test
    void testHandleGenericException() {
        // Given
        Exception ex = new Exception("An unexpected error occurred");

        // When
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleGenericException(ex);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("INTERNAL_SERVER_ERROR", responseEntity.getBody().getErrorCode());
        assertEquals("An unexpected error occurred", responseEntity.getBody().getMessage());
    }
}
