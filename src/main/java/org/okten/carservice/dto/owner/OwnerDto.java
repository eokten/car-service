package org.okten.carservice.dto.owner;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnerDto {

    private Long id;

    private String username;

    private String email;
}
