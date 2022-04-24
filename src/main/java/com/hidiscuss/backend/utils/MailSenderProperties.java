package com.hidiscuss.backend.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailSenderProperties {
    private String host;
    private int port;

    private String username;
    private String password;

    // process nested properties..
    private String propertiesMailSmtpAuth = "true";
    private String propertiesMailSmtpStarttlsEnable = "true";
}
