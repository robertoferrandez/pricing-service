package com.pricing.services.controller;

import com.pricing.services.exceptions.PriceNotFoundException;
import com.pricing.services.model.dto.PriceDto;
import com.pricing.services.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
@Validated
public class PriceController {

    private final PriceService priceService;

    @Operation(
            summary = "Get applicable price for product and brand",
            description = "Returns the current price for a product and brand on a given date."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price found successfully"),
            @ApiResponse(responseCode = "404", description = "No valid price found")
    })
    @GetMapping
    public PriceDto getPrice(
            @Parameter(description = "Query date (format: yyyy-MM-dd-HH.mm.ss)",
                    example = "2020-06-14-15.00.00")
            @RequestParam String date,

            @Parameter(description = "Product ID", example = "35455")
            @RequestParam(name = "product_id") Long productId,

            @Parameter(description = "Brand ID", example = "1")
            @RequestParam(name = "brand_id") Long brandId) {

        return priceService.getPriceForProductAndBrand(date, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException("No price found for the given parameters."));
    }
}