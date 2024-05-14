package org.okten.carservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {

    private String username;

    private String email;

    private String password;

    private String role;
}
