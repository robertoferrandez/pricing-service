package com.pricing.services.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents the composite primary key for the Price entity.
 * This class encapsulates the fields that form the composite key.
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class PriceId implements Serializable {

    /* Brand ID */
    private Long brandId;

    /* Product ID */
    private Long productId;

    /* Start date of the price validity */
    private LocalDateTime startDate;

    /* End date of the price validity */
    private LocalDateTime endDate;

    // Default constructor (needed by JPA)
    public PriceId() {}



}