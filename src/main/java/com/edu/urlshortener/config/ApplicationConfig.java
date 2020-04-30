package com.edu.urlshortener.config;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public UrlValidator urlValidator() {
        String[] schemes = {"http", "https"};
        return new UrlValidator(schemes);
    }
}
