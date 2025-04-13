package com.pricing.services.model.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * LoginDtoResponse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDtoResponse {

    @Schema(description = "the jwt", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0NDU2NTM1MCwiZXhwIjoxNzQ0NTY4OTUwfQ.fWLJfieiwO9U_Axt87aj0sVXJj-Qd9_hd1sd4ZNxr_o")
    private String jwt;

}