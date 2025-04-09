package com.pricing.services.service;

import com.pricing.services.mapper.PriceMapper;
import com.pricing.services.model.domain.Price;
import com.pricing.services.model.dto.PriceDto;
import com.pricing.services.model.entity.PriceEntity;
import com.pricing.services.policy.PricePolicy;
import com.pricing.services.repository.PriceRepository;
import com.pricing.services.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public Optional<PriceDto> getPriceForProductAndBrand(String date, Long productId, Long brandId) {

        log.info("Entering getPriceForProductAndBrand with productId: {}, brandId: {}, date: {}", productId, brandId, date);

        List<PriceEntity> pricesEntity = priceRepository.findValidPrices(productId, brandId, Utils.toLocalDateTime(date));
        List<Price> prices = PriceMapper.INSTANCE.entityToDomainList(pricesEntity);

        Optional<PriceDto> priceDto = PricePolicy.getHighestPriority(prices).map(PriceMapper.INSTANCE::domainToDto);
        log.info("Exiting getPriceForProductAndBrand with result: {}", priceDto.orElse(null));
        return priceDto;

    }

}