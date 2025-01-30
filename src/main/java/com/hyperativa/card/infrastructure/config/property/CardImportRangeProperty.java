package com.hyperativa.card.infrastructure.config.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardImportRangeProperty {

    private Integer begin;
    private Integer end;

}