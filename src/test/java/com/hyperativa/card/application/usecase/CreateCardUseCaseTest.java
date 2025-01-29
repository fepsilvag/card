package com.hyperativa.card.application.usecase;

import com.hyperativa.card.application.dto.CardInbound;
import com.hyperativa.card.application.dto.CardOutbound;
import com.hyperativa.card.application.mapper.CardEntityMapper;
import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.port.out.SaveCardPort;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Execute should create card")
    void execute_shouldCreateCard() {
        // Given
        CardInbound inbound = CardInbound.builder()
                .cardNumber("1234567890123456")
                .build();

        CardEntity entity = CardEntity.builder()
                .cardNumber(inbound.getCardNumber())
                .build();

        CardOutbound outbound = CardOutbound.builder()
                .cardNumber(inbound.getCardNumber())
                .build();

        // When
        when(cardEntityMapper.toEntity(inbound)).thenReturn(entity);
        when(saveCardPort.execute(entity)).thenReturn(entity);
        when(cardEntityMapper.toOutbound(entity)).thenReturn(outbound);

        // Then
        assertEquals(outbound, createCardUseCase.execute(inbound));
    }

}