package org.okten.carservice.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SignUpResponseDto {

    private Long id;

    private String username;
}
