package org.example.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")  // Указание точного источника
                .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE")  // Разрешенные методы
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
