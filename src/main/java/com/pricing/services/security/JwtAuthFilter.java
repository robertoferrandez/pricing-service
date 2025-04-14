package com.pricing.services.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.services.exceptions.errors.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        // If the endpoint is public, just continue with the request
        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get the Authorization header
        String authHeader = request.getHeader(AUTH_HEADER);

        // If the token is not present, return an error
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            writeErrorResponse(response, "Token not found");
            return;
        }

        // Extract the token from the header
        String token = authHeader.substring(7);

        // Validate the token
        if (!jwtUtil.isTokenValid(token)) {
            writeErrorResponse(response, "Invalid token");
            return;
        }

        // Extract the username from the token and set the authentication
        String username = jwtUtil.extractUsername(token);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, null, List.of())
        );

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String path = request.getRequestURI();
        return PublicEndpoints.LIST.stream().anyMatch(path::startsWith);
    }

    private void writeErrorResponse(HttpServletResponse response, String message) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse("401", message, LocalDateTime.now());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
