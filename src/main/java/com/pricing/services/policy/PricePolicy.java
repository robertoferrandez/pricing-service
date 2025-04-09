package com.pricing.services.policy;

import com.pricing.services.model.domain.Price;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PricePolicy {

    private PricePolicy() {}

    public static Optional<Price> getHighestPriority(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparingInt(Price::getPriority));
    }

    // Implementation of future methods
    // public static List<Price> filterByCurrency(...)
    // public static Optional<Price> getDefaultFallback(...)
}