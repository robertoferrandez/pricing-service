package com.pricing.services.controller;

import com.pricing.services.model.dto.PriceDto;
import com.pricing.services.service.PriceService;
import com.pricing.services.exceptions.custom.PriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    private static final String DATE = "2020-06-14-10.00.00";
    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;

    @Test
    void shouldReturnPriceDtoWhenValidRequest() {
        // Given a PriceDto returned by the service
        PriceDto priceDto = PriceDto.builder()
                .price(35.50)
                .build();

        // When the service is called, it returns the PriceDto
        when(priceService.getPriceForProductAndBrand(DATE, PRODUCT_ID, BRAND_ID))
                .thenReturn(Optional.of(priceDto));

        // Call the controller directly
        PriceDto result = priceController.getPrice(DATE, PRODUCT_ID, BRAND_ID);

        // Verify the result
        assertNotNull(result);
        assertEquals(35.50, result.getPrice());

        // Verify that the service was called with the correct parameters
        verify(priceService).getPriceForProductAndBrand(DATE, PRODUCT_ID, BRAND_ID);
    }

    @Test
    void shouldReturnNotFoundWhenPriceNotFound() {
        // When no price is found for the given parameters
        when(priceService.getPriceForProductAndBrand(DATE, PRODUCT_ID, BRAND_ID))
                .thenReturn(Optional.empty());

        // Call the controller directly and expect an exception
        PriceNotFoundException exception = assertThrows(PriceNotFoundException.class, () ->
            priceController.getPrice(DATE, PRODUCT_ID, BRAND_ID));

        // Verify that the exception has the correct message
        assertEquals("No price found for the given parameters.", exception.getMessage());

        // Verify that the service was called with the correct parameters
        verify(priceService).getPriceForProductAndBrand(DATE, PRODUCT_ID, BRAND_ID);
    }

    @Test
    void shouldReturnInternalServerErrorWhenUnexpectedError() {
        // Simulate an unexpected error when the service is called
        when(priceService.getPriceForProductAndBrand(DATE, PRODUCT_ID, BRAND_ID))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Call the controller directly and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            priceController.getPrice(DATE, PRODUCT_ID, BRAND_ID)
        );

        // Verify that the exception has the correct message
        assertEquals("Unexpected error", exception.getMessage());

        // Verify that the service was called
        verify(priceService).getPriceForProductAndBrand(DATE, PRODUCT_ID, BRAND_ID);
    }
}
