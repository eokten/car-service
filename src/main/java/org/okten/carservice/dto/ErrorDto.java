package org.okten.carservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorDto {

    private List<String> details;

    private LocalDateTime timestamp;
}
