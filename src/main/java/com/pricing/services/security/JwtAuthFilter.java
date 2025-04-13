package com.pricing.services.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.services.exceptions.errors.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Si el endpoint es público, simplemente pasa la solicitud
        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener el encabezado Authorization
        String authHeader = request.getHeader("Authorization");

        // Si el token no está presente, retornar un error
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            writeErrorResponse(response, "401", "Token not found");
            return;
        }

        // Extraer el token del encabezado
        String token = authHeader.substring(7);

        // Verificar si el token es válido
        if (!jwtUtil.isTokenValid(token)) {
            writeErrorResponse(response, "401", "Invalid token");
            return;
        }

        // Extraer el nombre de usuario del token y establecer la autenticación
        String username = jwtUtil.extractUsername(token);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, null, List.of())
        );

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String path = request.getRequestURI();
        return PublicEndpoints.LIST.stream().anyMatch(path::startsWith);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            return authHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private void authenticateUser(String token) {
        String username = jwtUtil.extractUsername(token);
        var auth = new UsernamePasswordAuthenticationToken(username, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void writeErrorResponse(HttpServletResponse response, String code, String message) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(code, message, LocalDateTime.now());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
