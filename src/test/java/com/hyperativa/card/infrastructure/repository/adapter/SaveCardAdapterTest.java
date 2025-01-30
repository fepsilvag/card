package com.hyperativa.card.infrastructure.repository.adapter;

import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.exception.CardNumberDuplicatedException;
import com.hyperativa.card.infrastructure.repository.CardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        CardEntity entity = CardEntity.builder()
                .cardNumber("1234567890123456")
                .build();

        // When
        when(cardRepository.save(entity)).thenReturn(entity);

        // Then
        assertEquals(entity, saveCardAdapter.execute(entity));
    }

    @Test
    @DisplayName("Execute should throw CardNumberDuplicatedException")
    void execute_shouldThrowCardNumberDuplicatedException() {
        // Given
        CardEntity entity = CardEntity.builder()
                .cardNumber("1234567890123456")
                .build();

        // When
        when(cardRepository.save(entity)).thenThrow(new DataIntegrityViolationException(""));

        // Then
        assertThrows(CardNumberDuplicatedException.class, () -> saveCardAdapter.execute(entity));
    }

}
