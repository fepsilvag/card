package com.hyperativa.card.domain.port.out;

import com.hyperativa.card.domain.domain.CardEntity;

public interface SaveCardPort {

    CardEntity execute(CardEntity entity);

}