package com.hyperativa.card.application.usecase;

import com.hyperativa.card.application.dto.CardInbound;
import com.hyperativa.card.application.dto.CardOutbound;
import com.hyperativa.card.application.mapper.CardEntityMapper;
import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.port.out.SaveCardPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class CreateCardUseCaseTest {

    @Mock
    private SaveCardPort saveCardPort;

    @Mock
    private CardEntityMapper cardEntityMapper;

    @InjectMocks
    private CreateCardUseCase createCardUseCase;

    @Test
    void execute_shouldCreateCard() {
        // Given
        CardInbound cardInbound = CardInbound.builder()
                .cardNumber("1234567890123456")
                .build();

        CardEntity cardEntity = CardEntity.builder()
                .cardNumber(cardInbound.getCardNumber())
                .build();

        CardOutbound cardOutbound = CardOutbound.builder()
                .cardNumber(cardInbound.getCardNumber())
                .build();

        // When
        when(cardEntityMapper.toEntity(cardInbound)).thenReturn(cardEntity);
        when(saveCardPort.execute(cardEntity)).thenReturn(cardEntity);
        when(cardEntityMapper.toOutbound(cardEntity)).thenReturn(cardOutbound);

        // Then
        assertEquals(cardOutbound, createCardUseCase.execute(cardInbound));
    }

}