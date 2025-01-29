package com.hyperativa.card.application.usecase;

import com.hyperativa.card.application.dto.CardInbound;
import com.hyperativa.card.application.dto.CardOutbound;
import com.hyperativa.card.application.mapper.CardEntityMapper;
import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.port.in.CreateCardPort;
import com.hyperativa.card.domain.port.out.SaveCardPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Log4j2
@Service
@Validated
@RequiredArgsConstructor
public class CreateCardUseCase implements CreateCardPort {

    private final SaveCardPort saveCardPort;
    private final CardEntityMapper cardEntityMapper;

    @Override
    public CardOutbound execute(@Valid CardInbound cardInbound) {
        CardEntity cardEntity = cardEntityMapper.toEntity(cardInbound);

        log.info("Creating card: {}", cardEntity);
        cardEntity = saveCardPort.execute(cardEntity);

        log.info("Card created: {}", cardEntity);
        return cardEntityMapper.toOutbound(cardEntity);
    }

}