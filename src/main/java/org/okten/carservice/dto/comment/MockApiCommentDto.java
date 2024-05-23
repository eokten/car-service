package org.okten.carservice.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MockApiCommentDto {

    private Long id;

    private String author;

    private String text;

    private LocalDateTime createdAt;
}
