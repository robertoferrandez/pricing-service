package com.pricing.services.policy;

import com.pricing.services.model.domain.PriceDomain;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PricePolicy {

    private PricePolicy() {}

    public static Optional<PriceDomain> getHighestPriority(List<PriceDomain> price) {
        return price.stream()
                .max(Comparator.comparingInt(PriceDomain::getPriority));
    }

    // Implementation of future methods
    // public static List<Price> filterByCurrency(...)
    // public static Optional<Price> getDefaultFallback(...)
}