package com.pricing.services.model.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * DTO to represent product prices in a simple way.
 * Used for data transfer between layers of the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceDto {

    @Schema(description = "Product ID", example = "35455")
    private Long productId;

    @Schema(description = "Brand ID", example = "1")
    private Long brandId;

    @Schema(description = "Start date of price validity", example = "2020-06-14-15.00.00")
    private String startDate;

    @Schema(description = "End date of price validity", example = "2020-12-31-23.59.59")
    private String endDate;

    @Schema(description = "Price of the product", example = "35.50")
    private Double price;

    @Schema(description = "Currency in which the price is set", example = "EUR")
    private String currency;
}