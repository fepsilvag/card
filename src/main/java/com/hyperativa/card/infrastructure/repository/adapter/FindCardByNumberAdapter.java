package com.hyperativa.card.infrastructure.repository.adapter;

import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.port.out.FindCardByNumberPort;
import com.hyperativa.card.infrastructure.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindCardByNumberAdapter implements FindCardByNumberPort {

    private final CardRepository cardRepository;

    @Override
    public CardEntity execute(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }

}
