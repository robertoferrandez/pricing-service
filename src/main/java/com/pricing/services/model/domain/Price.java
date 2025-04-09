package com.pricing.services.model.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Domain model that represents the price of a product.
 * Encapsulates the business logic related to the price.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Price {

    /**
     * The ID of the product for which the price is applicable.
     */
    private Long productId;

    /**
     * The ID of the brand for which the price is applicable.
     */
    private Long brandId;

    /**
     * The price value for the product.
     */
    private Double price;

    /**
     * The currency in which the price is represented (e.g., EUR).
     */
    private String currency;

    /**
     * The priority of the price (used when multiple prices exist for a product).
     */
    private Integer priority;

    /**
     * The start date of the price validity.
     */
    private LocalDateTime startDate;

    /**
     * The end date of the price validity.
     */
    private LocalDateTime endDate;

    /**
     * Checks if the price is valid at the given date.
     *
     * @param date The date to check the price validity.
     * @return true if the price is valid at the given date, false otherwise.
     */
    public boolean isValidAt(LocalDateTime date) {
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }


}


