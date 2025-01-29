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
class SaveCardAdapterTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private SaveCardAdapter saveCardAdapter;

    @Test
    @DisplayName("Execute should save card")
    void execute_shouldSaveCard() {
        // Given
        CardEntity cardEntity = CardEntity.builder()
                .cardNumber("1234567890123456")
                .build();

        // When
        when(cardRepository.save(cardEntity)).thenReturn(cardEntity);

        // Then
        assertEquals(cardEntity, saveCardAdapter.execute(cardEntity));
    }

}
