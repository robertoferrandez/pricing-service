package com.pricing.services.service;

import com.pricing.services.mapper.PriceMapper;
import com.pricing.services.model.domain.PriceDomain;
import com.pricing.services.model.dto.prices.PriceDto;
import com.pricing.services.model.entity.PriceEntity;
import com.pricing.services.repository.PriceRepository;
import com.pricing.services.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    private final PriceMapper priceMapper;

    public Optional<PriceDto> getPriceForProductAndBrand(String date, Long productId, Long brandId) {

        log.info("Entering getPriceForProductAndBrand with productId: {}, brandId: {}, date: {}", productId, brandId, date);

        List<PriceEntity> pricesEntity = priceRepository.findValidPrices(productId, brandId, Utils.toLocalDateTime(date));
        List<PriceDomain> pricesDomain = priceMapper.entityToDomainList(pricesEntity);
        Optional<PriceDto> priceDto = getHighestPriorityPrice(pricesDomain).map(priceMapper::domainToDto);
        log.info("Exiting getPriceForProductAndBrand with result: {}", priceDto.orElse(null));
        return priceDto;

    }

    private Optional<PriceDomain> getHighestPriorityPrice(List<PriceDomain> pricesDomain) {
        return pricesDomain.stream()
                .max(Comparator.comparingInt(PriceDomain::getPriority));
    }

}