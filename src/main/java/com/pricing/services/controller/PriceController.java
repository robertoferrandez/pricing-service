package com.pricing.services.controller;

import com.pricing.services.exceptions.PriceNotFoundException;
import com.pricing.services.exceptions.errors.ErrorResponse;
import com.pricing.services.model.dto.PriceDto;
import com.pricing.services.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
@Validated
public class PriceController {

    private final PriceService priceService;

    // Expresión regular para el formato "yyyy-MM-dd-HH.mm.ss"
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}-\\d{2}\\.\\d{2}\\.\\d{2}";

    @Operation(
            summary = "Get applicable price for product and brand",
            description = "Returns the current price for a product and brand on a given date."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No valid price found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public PriceDto getPrice(
            @Parameter(description = "Query date (format: yyyy-MM-dd-HH.mm.ss)",
                    example = "2020-06-14-15.00.00")
            @Pattern(regexp = DATE_PATTERN, message = "Invalid date format. Expected format: yyyy-MM-dd-HH.mm.ss")
            @Valid String date,

            @Parameter(description = "Product ID", example = "35455")
            @RequestParam(name = "product_id") Long productId,

            @Parameter(description = "Brand ID", example = "1")
            @RequestParam(name = "brand_id") Long brandId) {

        return priceService.getPriceForProductAndBrand(date, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException("No price found for the given parameters."));
    }
}