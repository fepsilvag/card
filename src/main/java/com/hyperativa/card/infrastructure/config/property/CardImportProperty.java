package com.hyperativa.card.infrastructure.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "card-import")
public class CardImportProperty {

    private CardImportRangeProperty lineIdentification;
    private CardImportRangeProperty batchNumber;
    private CardImportRangeProperty cardNumber;

}