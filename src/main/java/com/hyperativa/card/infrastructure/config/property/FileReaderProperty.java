package com.hyperativa.card.infrastructure.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "file-reader")
public class FileReaderProperty {

    private Integer batchSize;
    private String delimiterCharacter;

}