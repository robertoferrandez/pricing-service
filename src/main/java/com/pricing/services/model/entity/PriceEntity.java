package com.pricing.services.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Represents the entity mapping the 'prices' table in the database.
 * Contains product price details.
 */
@Entity
@Table(name = "prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PriceId.class)  // Specifies PriceId as the composite primary key
public class PriceEntity {

    @Id
    /* Brand ID */
    private Long brandId;

    @Id
    /* Product ID */
    private Long productId;

    @Id
    /* Start date of the price validity */
    private LocalDateTime startDate;

    @Id
    /* End date of the price validity */
    private LocalDateTime endDate;

    /* Price list ID */
    private Integer priceList;

    /* Price priority */
    private Integer priority;

    /* Product price */
    private Double price;

    /* Currency in which the price is stated */
    private String currency;
}