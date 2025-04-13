package com.pricing.services.controller;


import com.pricing.services.exceptions.custom.InvalidLoginException;
import com.pricing.services.exceptions.errors.ErrorResponse;
import com.pricing.services.model.dto.Login;
import com.pricing.services.model.dto.PriceDto;
import com.pricing.services.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {


    private final JwtUtil jwtUtil;
    @Operation(
            summary = "Get user token",
            description = "Returns the user token through the username and password"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",  description = "Price found successfully",
                    content =  @Content(mediaType = "application/json", schema = @Schema(implementation = PriceDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login login) {


        if ("admin".equals(login.getUsername()) && "1234".equals(login.getPassword())) {
            String token = jwtUtil.generateToken(login.getUsername());
            return ResponseEntity.ok(token);
        }

        throw new InvalidLoginException("Invalid username or password");
    }

}