package com.hyperativa.card.infrastructure.repository.adapter;

import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.port.out.SaveCardPort;
import com.hyperativa.card.infrastructure.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveCardAdapter implements SaveCardPort {

    private final CardRepository cardRepository;

    @Override
    public CardEntity execute(CardEntity entity) {
        return cardRepository.save(entity);
    }

}
