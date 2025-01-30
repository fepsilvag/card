package com.hyperativa.card.infrastructure.repository.adapter;

import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.exception.CardNumberDuplicatedException;
import com.hyperativa.card.domain.port.out.SaveCardPort;
import com.hyperativa.card.infrastructure.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveCardAdapter implements SaveCardPort {

    private final CardRepository cardRepository;

    @Override
    public CardEntity execute(CardEntity entity) {
        try {
            log.info("Creating card: {}", entity);
            return entity = cardRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            log.warn("Card number already in use: {}", entity.getCardNumber());
            throw new CardNumberDuplicatedException();
        }
    }

}
