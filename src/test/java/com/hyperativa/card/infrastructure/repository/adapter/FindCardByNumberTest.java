package com.hyperativa.card.infrastructure.repository.adapter;

import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.infrastructure.repository.CardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class FindCardByNumberTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private FindCardByNumberAdapter findCardByNumberAdapter;

    @Test
    @DisplayName("Execute should find card by number")
    void execute_shouldFindCardByNumber() {
        // Given
        CardEntity entity = CardEntity.builder()
                .cardNumber("1234567890123456")
                .build();

        // When
        when(cardRepository.findByCardNumber(entity.getCardNumber())).thenReturn(entity);

        // Then
        assertEquals(entity, findCardByNumberAdapter.execute(entity.getCardNumber()));
    }

}
