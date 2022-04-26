package com.hidiscuss.backend.controller.dto;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class SendEmailDto {
    @Email
    private String to;

    @NotNull
    private String subject;

    @NotNull
    private String text;

    public void applyHelper(MimeMessageHelper mimeMessageHelper) throws MessagingException {
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, true); // TODO debug.. encoding
    }
}
