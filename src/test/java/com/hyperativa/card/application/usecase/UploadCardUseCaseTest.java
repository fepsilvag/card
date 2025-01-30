package com.hyperativa.card.application.usecase;

import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.exception.CardNumberMandatoryException;
import com.hyperativa.card.domain.exception.EmptyRowException;
import com.hyperativa.card.domain.port.in.ReadFilePort;
import com.hyperativa.card.domain.port.out.SaveCardPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class UploadCardUseCaseTest {

    @Mock
    private ReadFilePort readFilePort;

    @Mock
    private SaveCardPort saveCardPort;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private UploadCardUseCase uploadCardUseCase;

    @Test
    @DisplayName("Execute should search card")
    void execute_shouldSearchCard() {
        // Given
        CardEntity entity = CardEntity.builder().build();

        List<String[]> fileLines = List.of(
                new String[]{"4716523987654321"},
                new String[]{"1234567890123456"});

        // When
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("testfile.txt");
        when(saveCardPort.execute(any())).thenReturn(entity);

        // Then
        assertDoesNotThrow(() -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw EmptyRowException with null row")
    void execute_shouldThrowEmptyRowException1() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String[]> fileLines = new ArrayList<>();
        fileLines.add(null);
        fileLines.add(new String[]{"1234567890123456"});

        // When
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(EmptyRowException.class, () -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw EmptyRowException with empty row")
    void execute_shouldThrowEmptyRowException2() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String[]> fileLines = List.of(
                new String[]{},
                new String[]{"1234567890123456"});

        // When
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(EmptyRowException.class, () -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw CardNumberMandatoryException with null card number")
    void execute_shouldThrowCardNumberMandatoryException1() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String[]> fileLines = List.of(
                new String[]{null},
                new String[]{"1234567890123456"});

        // When
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(CardNumberMandatoryException.class, () -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw CardNumberMandatoryException with empty card number")
    void execute_shouldThrowCardNumberMandatoryException2() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String[]> fileLines = List.of(
                new String[]{""},
                new String[]{"1234567890123456"});

        // When
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(CardNumberMandatoryException.class, () -> uploadCardUseCase.execute(file));
    }

}