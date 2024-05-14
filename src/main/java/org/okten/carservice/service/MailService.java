package org.okten.carservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.okten.carservice.dto.mail.MailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final MailSender mailSender;

    @Value("${mail.sender.username}")
    private String mailSenderUsername;

    public void sendMail(MailDto mailDto) {
        log.info("Sending mail with subject '{}' to {}", mailDto.getSubject(), mailDto.getRecipients());

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject(mailDto.getSubject());
        message.setText(mailDto.getBody());

        message.setFrom(mailSenderUsername);
        message.setTo(mailDto.getRecipients().toArray(String[]::new));

        mailSender.send(message);
    }
}
