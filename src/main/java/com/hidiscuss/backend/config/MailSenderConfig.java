package com.hidiscuss.backend.config;

import com.hidiscuss.backend.utils.MailSenderProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailSenderConfig {

    private final MailSenderProperties mailSenderProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSenderProperties.getHost());
        mailSender.setPort(mailSenderProperties.getPort());

        mailSender.setUsername(mailSenderProperties.getUsername());
        mailSender.setPassword(mailSenderProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailSenderProperties.getPropertiesMailSmtpAuth());
        props.put("mail.smtp.starttls.enable", mailSenderProperties.getPropertiesMailSmtpStarttlsEnable());

        return mailSender;

    }
}
