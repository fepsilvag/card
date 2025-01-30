package com.hyperativa.card.application.usecase;

import com.hyperativa.card.application.dto.CardInbound;
import com.hyperativa.card.application.dto.CardOutbound;
import com.hyperativa.card.application.mapper.CardEntityMapper;
import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.port.in.CreateCardPort;
import com.hyperativa.card.domain.port.out.SaveCardPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CreateCardUseCase implements CreateCardPort {

    private final SaveCardPort saveCardPort;
    private final CardEntityMapper cardEntityMapper;

    @Override
    public CardOutbound execute(@Valid CardInbound inbound) {
        CardEntity entity = cardEntityMapper.toEntity(inbound);
        entity = saveCardPort.execute(entity);

        log.info("Card created: {}", entity);
        return cardEntityMapper.toOutbound(entity);
    }

}