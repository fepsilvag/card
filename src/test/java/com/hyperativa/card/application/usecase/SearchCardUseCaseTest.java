package com.hyperativa.card.application.usecase;

import com.hyperativa.card.application.dto.CardOutbound;
import com.hyperativa.card.application.mapper.CardEntityMapper;
import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.port.out.FindCardByNumberPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class SearchCardUseCaseTest {

    @Mock
    private CardEntityMapper cardEntityMapper;

    @Mock
    private FindCardByNumberPort findCardByNumberPort;

    @InjectMocks
    private SearchCardUseCase searchCardUseCase;

    @Test
    @DisplayName("Execute should search card")
    void execute_shouldSearchCard() {
        // Given
        CardEntity entity = CardEntity.builder()
                .cardNumber("1234567890123456")
                .build();

        CardOutbound outbound = CardOutbound.builder()
                .cardNumber(entity.getCardNumber())
                .build();

        // When
        when(findCardByNumberPort.execute(entity.getCardNumber())).thenReturn(entity);
        when(cardEntityMapper.toOutbound(entity)).thenReturn(outbound);

        // Then
        assertEquals(outbound, searchCardUseCase.execute(entity.getCardNumber()));
    }

}