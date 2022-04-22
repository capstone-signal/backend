package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.SendEmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void send(@Valid SendEmailDto sendEmailDto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            sendEmailDto.applyHelper(mimeMessageHelper);
        } catch (Exception e) {
            log.error("send email error", e);
            throw new RuntimeException(e); // TODO change to custom exception
        }
        javaMailSender.send(mimeMessage);
    }

    public void sendBatch(@Valid List<SendEmailDto> sendEmailDtos) {
        throw new UnsupportedOperationException();
    }
}