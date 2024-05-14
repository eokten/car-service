package org.okten.carservice.dto.mail;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MailDto {

    private String subject;

    private String body;

    private List<String> recipients;
}
