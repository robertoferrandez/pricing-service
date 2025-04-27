package com.pricing.services.service;

import com.pricing.services.mapper.PriceMapper;
import com.pricing.services.model.domain.PriceDomain;
import com.pricing.services.model.dto.prices.PriceDto;
import com.pricing.services.model.entity.PriceEntity;
import com.pricing.services.repository.PriceRepository;
import com.pricing.services.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceServiceImpl priceService;

    private static final String DATE = "2020-06-14-10.00.00";
    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;

    @BeforeEach
    void setUp() {
        // Inicialización previa a cada test
    }

    @Test
    void shouldReturnPriceWhenValidPriceFound() {
        PriceEntity priceEntity = PriceEntity.builder()
                .price(35.50)
                .build();

        List<PriceEntity> priceEntities = List.of(priceEntity);

        PriceDomain priceDomain = PriceDomain.builder()
                .price(35.50)
                .build();

        PriceDto priceDto = PriceDto.builder()
                .price(35.50)
                .build();

        when(priceRepository.findValidPrices(PRODUCT_ID, BRAND_ID, Utils.toLocalDateTime(DATE)))
                .thenReturn(priceEntities);
        when(priceMapper.entityToDomainList(priceEntities)).thenReturn(List.of(priceDomain));
        when(priceMapper.domainToDto(priceDomain)).thenReturn(priceDto);

        Optional<PriceDto> result = priceService.getPriceForProductAndBrand(DATE, PRODUCT_ID, BRAND_ID);

        assertTrue(result.isPresent());
        assertEquals(35.50, result.get().getPrice());

        verify(priceRepository).findValidPrices(PRODUCT_ID, BRAND_ID, Utils.toLocalDateTime(DATE));
        verify(priceMapper).entityToDomainList(priceEntities);
        verify(priceMapper).domainToDto(priceDomain);
    }

    @Test
    void shouldReturnEmptyWhenNoPricesFound() {
        when(priceRepository.findValidPrices(PRODUCT_ID, BRAND_ID, Utils.toLocalDateTime(DATE)))
                .thenReturn(List.of());

        Optional<PriceDto> result = priceService.getPriceForProductAndBrand(DATE, PRODUCT_ID, BRAND_ID);

        assertFalse(result.isPresent());

        verify(priceRepository).findValidPrices(PRODUCT_ID, BRAND_ID, Utils.toLocalDateTime(DATE));
    }

    @Test
    void shouldReturnEmptyWhenNoPriceWithHighestPriority() {
        PriceEntity priceEntity = PriceEntity.builder()
                .price(25.45)
                .build();

        List<PriceEntity> priceEntities = List.of(priceEntity);

        PriceDomain priceDomain = PriceDomain.builder()
                .price(25.45)
                .build();

        when(priceRepository.findValidPrices(PRODUCT_ID, BRAND_ID, Utils.toLocalDateTime(DATE)))
                .thenReturn(priceEntities);
        when(priceMapper.entityToDomainList(priceEntities)).thenReturn(List.of(priceDomain));

        Optional<PriceDto> result = priceService.getPriceForProductAndBrand(DATE, PRODUCT_ID, BRAND_ID);

        assertFalse(result.isPresent());

        verify(priceRepository).findValidPrices(PRODUCT_ID, BRAND_ID, Utils.toLocalDateTime(DATE));
        verify(priceMapper).entityToDomainList(priceEntities);
    }

    @Test
    void shouldHandleExceptionWhenRepositoryFails() {
        when(priceRepository.findValidPrices(PRODUCT_ID, BRAND_ID, Utils.toLocalDateTime(DATE)))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> priceService.getPriceForProductAndBrand(DATE, PRODUCT_ID, BRAND_ID));

        verify(priceRepository).findValidPrices(PRODUCT_ID, BRAND_ID, Utils.toLocalDateTime(DATE));
    }
}
