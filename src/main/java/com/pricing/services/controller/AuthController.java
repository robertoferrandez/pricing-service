package com.pricing.services.controller;


import com.pricing.services.exceptions.custom.InvalidLoginException;
import com.pricing.services.exceptions.errors.ErrorResponse;
import com.pricing.services.model.dto.login.LoginDto;
import com.pricing.services.model.dto.login.LoginDtoResponse;
import com.pricing.services.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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
            @ApiResponse(responseCode = "200",  description = "Successfully logged in",
                    content =  @Content(mediaType = "application/json", schema = @Schema(implementation = LoginDtoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"errorCode\":\"400\",\"message\":\"Invalid username or password.\",\"timestamp\":\"2025-04-14T10:00:00\"}")
                    ))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginDtoResponse> login(@RequestBody LoginDto loginDto) {
        LoginDtoResponse response = new LoginDtoResponse();

        if ("admin".equals(loginDto.getUsername()) && "1234".equals(loginDto.getPassword())) {
            response.setJwt(jwtUtil.generateToken(loginDto.getUsername()));
            return ResponseEntity.ok(response);
        }

        throw new InvalidLoginException("Invalid username or password");
    }

}