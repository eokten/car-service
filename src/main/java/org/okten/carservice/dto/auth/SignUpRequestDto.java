package org.okten.carservice.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequestDto {

    private String username;

    private String email;

    private String password;

    private String role;
}
