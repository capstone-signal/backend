package com.hidiscuss.backend.config;

import com.hidiscuss.backend.config.interceptor.GitHubClearInterceptor;
import com.hidiscuss.backend.entity.StringToEnumConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${spring.profiles.active}")
    private String profile;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GitHubClearInterceptor()).order(Integer.MIN_VALUE);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(profile.equals("dev") ? "http://localhost:3000" : "https://hidiscuss.ga")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }
}
