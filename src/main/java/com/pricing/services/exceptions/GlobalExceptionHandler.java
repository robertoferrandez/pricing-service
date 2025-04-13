package com.pricing.services.exceptions;

import com.pricing.services.exceptions.custom.InvalidLoginException;
import com.pricing.services.exceptions.custom.PriceNotFoundException;
import com.pricing.services.exceptions.errors.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the PriceNotFoundException and returns a 404 Not Found error with a custom message.
     * @param ex the exception thrown
     * @return the response entity with the error details
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePriceNotFoundException(PriceNotFoundException ex) {
        // Creating a custom error response for price not found
        ErrorResponse errorResponse = new ErrorResponse("PRICE_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);  // HTTP 404
    }

    /**
     * Handles MissingServletRequestParameterException and returns a 400 Bad Request error
     * with a message indicating which parameter is missing.
     * @param ex the exception thrown
     * @return the response entity with the error details
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        // Extracting the missing parameter's name and creating a custom error message
        String paramName = ex.getParameterName();
        String errorMessage = "Required request parameter '" + paramName + "' is missing";

        // Building the error response with a timestamp
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("400")  // HTTP 400 Bad Request
                .message(errorMessage)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    /**
     * Handles MissingServletRequestParameterException and returns a 400 Bad Request error
     * with a message indicating which parameter is missing.
     * @param ex the exception thrown
     * @return the response entity with the error details
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleWrongParameters(ConstraintViolationException ex) {

        // Building the error response with a timestamp
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("400")  // HTTP 400 Bad Request
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MissingServletRequestParameterException and returns a 400 Bad Request error
     * with a message indicating bad login parameters
     * @param ex the exception thrown
     * @return the response entity with the error details
     */
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorResponse> handleWrongLogin(ConstraintViolationException ex) {

        // Building the error response with a timestamp
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("400")  // HTTP 400 Bad Request
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general exceptions and returns a 500 Internal Server Error with a generic message.
     * @param ex the exception thrown
     * @return the response entity with the error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        // Creating a generic error response for unexpected exceptions
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);  // HTTP 500
    }
}