package com.pricing.services.service;

import com.pricing.services.model.dto.prices.PriceDto;

import java.util.Optional;

/**
 * Interface defining the methods for the price service.
 */
public interface PriceService {

    /**
     * Retrieves the applicable price for a product and brand at a specific date.
     *
     * @param date      The date in "yyyy-MM-dd-HH.mm.ss" format for the price query.
     * @param productId The product ID.
     * @param brandId   The brand ID.
     * @return An {@link PriceDto} with the found price, or empty if no price is found.
     */
    Optional<PriceDto> getPriceForProductAndBrand(String date, Long productId, Long brandId);
}