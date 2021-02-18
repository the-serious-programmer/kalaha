package com.bol.kalaha.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private static final String ALL_ENDPOINTS = "/**";
    private static final String ALLOWED_ORIGINS = "http://localhost:4200";
    private static final String[] ALLOWED_METHODS = new String[] {
        "GET", "POST", "PUT", "DELETE", "OPTIONS"
    };

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry
            .addMapping(ALL_ENDPOINTS)
            .allowedOrigins(ALLOWED_ORIGINS)
            .allowedMethods(ALLOWED_METHODS);
    }
}
