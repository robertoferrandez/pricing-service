package com.pricing.services.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.services.security.JwtAuthFilter;
import com.pricing.services.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {

    @Bean
    public JwtAuthFilter jwtFilter(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        return new JwtAuthFilter(jwtUtil,objectMapper);
    }
}