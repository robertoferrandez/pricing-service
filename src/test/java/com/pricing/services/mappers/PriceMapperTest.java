package com.pricing.services.mappers;

import com.pricing.services.mapper.PriceMapper;
import com.pricing.services.model.domain.PriceDomain;
import com.pricing.services.model.dto.PriceDto;
import com.pricing.services.model.entity.PriceEntity;
import com.pricing.services.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PriceMapperTest {

    private PriceMapper priceMapper;

    @BeforeEach
    void setUp() {
        priceMapper = Mappers.getMapper(PriceMapper.class);
    }

    private PriceEntity createPriceEntity(Long brandId, Long productId, LocalDateTime startDate, LocalDateTime endDate, Integer priceList, Integer priority, Double price, String currency) {
        return PriceEntity.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(startDate)
                .endDate(endDate)
                .priceList(priceList)
                .priority(priority)
                .price(price)
                .currency(currency)
                .build();
    }

    private PriceDomain createPriceDomain(Long brandId, Long productId, LocalDateTime startDate, LocalDateTime endDate, Integer priceList, Integer priority, Double price, String currency) {
        return PriceDomain.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(priceList)
                .startDate(startDate)
                .endDate(endDate)
                .priority(priority)
                .price(price)
                .currency(currency)
                .build();
    }

    private PriceDto createPriceDto(String startDate, String endDate, Double price, String currency) {
        return PriceDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .price(price)
                .currency(currency)
                .build();
    }

    @Test
    void shouldMapEntityToDomain() {
        PriceEntity entity = createPriceEntity(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0, 0, 0), LocalDateTime.of(2020, 6, 14, 18, 0, 0, 0), 1, 1, 35.50, "EUR");
        PriceDomain domain = priceMapper.entityToDomain(entity);

        assertThat(domain).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    void shouldMapEntityToDomainList() {
        PriceEntity entity1 = createPriceEntity(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0, 0, 0), LocalDateTime.of(2020, 6, 14, 18, 0, 0, 0), 1, 1, 35.50, "EUR");
        PriceEntity entity2 = createPriceEntity(1L, 35456L, LocalDateTime.of(2020, 6, 15, 10, 0, 0, 0), LocalDateTime.of(2020, 6, 15, 18, 0, 0, 0), 2, 2, 45.50, "USD");
        List<PriceDomain> domains = priceMapper.entityToDomainList(List.of(entity1, entity2));

        assertThat(domains).hasSize(2)
                .extracting("productId")
                .containsExactlyInAnyOrder(35455L, 35456L);
    }

    @Test
    void shouldMapDomainToEntity() {
        PriceDomain domain = createPriceDomain(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0, 0, 0), LocalDateTime.of(2020, 6, 14, 18, 0, 0, 0), 1, 1, 35.50, "EUR");
        PriceEntity entity = priceMapper.domainToEntity(domain);

        assertThat(entity).usingRecursiveComparison().isEqualTo(domain);
    }

    @Test
    void shouldMapDomainToDto() {
        PriceDomain domain = createPriceDomain(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0, 0, 0), LocalDateTime.of(2020, 6, 14, 18, 0, 0, 0), 1, 1, 35.50, "EUR");
        PriceDto dto = priceMapper.domainToDto(domain);

        assertThat(dto.getStartDate()).isEqualTo("2020-06-14-10.00.00");
        assertThat(dto.getEndDate()).isEqualTo("2020-06-14-18.00.00");
        assertThat(dto.getPrice()).isEqualTo(35.50);
        assertThat(dto.getCurrency()).isEqualTo("EUR");
    }

    @Test
    void shouldMapDtoToDomain() {
        PriceDto dto = createPriceDto("2020-06-14-10.00.00", "2020-06-14-18.00.00", 35.50, "EUR");
        PriceDomain domain = priceMapper.dtoToDomain(dto);

        assertThat(domain.getStartDate()).isEqualTo(Utils.toLocalDateTime("2020-06-14-10.00.00"));
        assertThat(domain.getEndDate()).isEqualTo(Utils.toLocalDateTime("2020-06-14-18.00.00"));
        assertThat(domain.getPrice()).isEqualTo(35.50);
        assertThat(domain.getCurrency()).isEqualTo("EUR");
    }
}
