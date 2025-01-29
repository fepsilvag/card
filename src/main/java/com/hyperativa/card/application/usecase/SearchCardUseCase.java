package com.hyperativa.card.application.usecase;

import com.hyperativa.card.application.dto.CardOutbound;
import com.hyperativa.card.application.mapper.CardEntityMapper;
import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.port.in.SearchCardPort;
import com.hyperativa.card.domain.port.out.FindCardByNumberPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Log4j2
@Service
@Validated
@RequiredArgsConstructor
public class SearchCardUseCase implements SearchCardPort {

    private final CardEntityMapper cardEntityMapper;
    private final FindCardByNumberPort findCardByNumberPort;

    @Override
    public CardOutbound execute(String cardNumber) {
        log.info("Searching card by number: {}", cardNumber);
        CardEntity cardEntity = findCardByNumberPort.execute(cardNumber);

        log.info("Card found: {}", cardEntity);
        return cardEntityMapper.toOutbound(cardEntity);
    }

}