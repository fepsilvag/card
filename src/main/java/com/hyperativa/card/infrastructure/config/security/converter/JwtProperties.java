package com.hyperativa.card.infrastructure.config.security.converter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class JwtProperties {

    private String resourceId;
    private String principalAttribute;

}