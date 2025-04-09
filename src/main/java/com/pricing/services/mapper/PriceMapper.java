package com.pricing.services.mapper;

import com.pricing.services.model.domain.Price;
import com.pricing.services.model.dto.PriceDto;
import com.pricing.services.model.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * Mapper responsible for converting between entities, domain models, and DTOs.
 * It facilitates the transformation of data between internal and external representations.
 */
@Mapper
public interface PriceMapper {

    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    /**
     * Converts a PriceEntity to a domain model Price.
     *
     * @param entity The price entity
     * @return The domain model Price
     */
    Price entityToDomain(PriceEntity entity);


    /**
     * Converts a list of PriceEntity to a list of domain model Price.
     *
     * @param entity The list of price entities
     * @return The list of domain model Prices
     */
    List<Price> entityToDomainList(List<PriceEntity> entity);

    /**
     * Converts a domain model Price to a PriceEntity.
     *
     * @param domain The domain model Price
     * @return The PriceEntity
     */
    PriceEntity domainToEntity(Price domain);

    /**
     * Converts a domain model Price to a PriceDto.
     *
     * @param domain The domain model Price
     * @return The PriceDto
     */
    PriceDto domainToDto(Price domain);

    /**
     * Converts a PriceDto to a domain model Price.
     *
     * @param dto The PriceDto
     * @return The domain model Price
     */
    Price dtoToDomain(PriceDto dto);
}