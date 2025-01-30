package com.hyperativa.card.domain.port.in;

import com.hyperativa.card.application.dto.CardInbound;
import com.hyperativa.card.application.dto.CardOutbound;
import jakarta.validation.Valid;

public interface CreateCardPort {

    CardOutbound execute(@Valid CardInbound inbound);

}