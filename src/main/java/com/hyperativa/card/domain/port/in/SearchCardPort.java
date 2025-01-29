package com.hyperativa.card.domain.port.in;

import com.hyperativa.card.application.dto.CardOutbound;

public interface SearchCardPort {

    CardOutbound execute(String cardNumber);

}