package com.pricing.services.repository;


import com.pricing.services.model.entity.PriceEntity;
import com.pricing.services.model.entity.PriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, PriceId> {

    @Query("SELECT p FROM PriceEntity p " +
            "WHERE p.productId = :productId " +
            "AND p.brandId = :brandId " +
            "AND p.startDate <= :queryDate " +
            "AND p.endDate >= :queryDate")
    List<PriceEntity> findValidPrices(@Param("productId") Long productId,
                                      @Param("brandId") Long brandId,
                                      @Param("queryDate") LocalDateTime queryDate);
}