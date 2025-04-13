package com.pricing.services.mapper;

import com.pricing.services.model.domain.PriceDomain;
import com.pricing.services.model.dto.prices.PriceDto;
import com.pricing.services.model.entity.PriceEntity;
import com.pricing.services.utils.Utils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
/**
 * Mapper responsible for converting between entities, domain models, and DTOs.
 * It facilitates the transformation of data between internal and external representations.
 */
@Mapper(componentModel = "spring", imports = {Utils.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PriceMapper {

    /**
     * Converts a PriceEntity to a domain model Price.
     *
     * @param entity The price entity
     * @return The domain model Price
     */
    PriceDomain entityToDomain(PriceEntity entity);


    /**
     * Converts a list of PriceEntity to a list of domain model Price.
     *
     * @param entity The list of price entities
     * @return The list of domain model Prices
     */
    List<PriceDomain> entityToDomainList(List<PriceEntity> entity);

    /**
     * Converts a domain model Price to a PriceEntity.
     *
     * @param domain The domain model Price
     * @return The PriceEntity
     */
    PriceEntity domainToEntity(PriceDomain domain);

    /**
     * Converts a domain model Price to a PriceDto.
     *
     * @param domain The domain model Price
     * @return The PriceDto
     */
    @Mapping(target = "startDate", expression = "java(Utils.fromLocalDateTime(domain.getStartDate()))")
    @Mapping(target = "endDate", expression = "java(Utils.fromLocalDateTime(domain.getEndDate()))")
    PriceDto domainToDto(PriceDomain domain);

    /**
     * Converts a PriceDto to a domain model Price.
     *
     * @param dto The PriceDto
     * @return The domain model Price
     */
    @Mapping(target = "startDate", expression = "java(Utils.toLocalDateTime(dto.getStartDate()))")
    @Mapping(target = "endDate", expression = "java(Utils.toLocalDateTime(dto.getEndDate()))")
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "priceList", ignore = true)
    PriceDomain dtoToDomain(PriceDto dto);
}