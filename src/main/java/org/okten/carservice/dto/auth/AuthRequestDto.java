package org.okten.carservice.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
