package org.okten.carservice.dto.owner;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOwnerRequest {

    private String username;

    private String email;
}
