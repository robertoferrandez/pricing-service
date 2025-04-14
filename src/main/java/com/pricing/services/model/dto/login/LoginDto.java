package com.pricing.services.model.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO to represent login attributes in a simple way.
 * LoginDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    @Schema(description = "the username", example = "admin")
    private String username;

    @Schema(description = "the password", example = "1234")
    private String password;

}