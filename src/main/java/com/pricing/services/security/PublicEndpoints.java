package com.pricing.services.security;

import java.util.List;

public class PublicEndpoints {
    public static final List<String> LIST = List.of(
            "/login",
            "/swagger-ui",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**"
    );
}