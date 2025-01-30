package com.hyperativa.card.application.usecase;

import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.exception.*;
import com.hyperativa.card.domain.port.in.ReadFilePort;
import com.hyperativa.card.domain.port.out.FindCardByNumberPort;
import com.hyperativa.card.domain.port.out.SaveCardPort;
import com.hyperativa.card.infrastructure.config.property.CardImportProperty;
import com.hyperativa.card.infrastructure.config.property.CardImportRangeProperty;
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
    private CardImportProperty cardImportProperty;

    @Mock
    private FindCardByNumberPort findCardByNumberPort;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private UploadCardUseCase uploadCardUseCase;

    @Test
    @DisplayName("Execute should search card")
    void execute_shouldSearchCard() {
        // Given
        CardEntity entity = CardEntity.builder().build();

        List<String> fileLines = List.of(
                "C3     4456897999999999",
                "C4     4456897998199999");

        // When
        setUp();
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("testfile.txt");
        when(findCardByNumberPort.execute("4456897999999999")).thenReturn(entity);
        when(findCardByNumberPort.execute("4456897998199999")).thenReturn(null);
        when(saveCardPort.execute(any())).thenReturn(entity);

        // Then
        assertDoesNotThrow(() -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw EmptyRowException with null row")
    void execute_shouldThrowEmptyRowException1() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String> fileLines = new ArrayList<>();
        fileLines.add(null);
        fileLines.add("C3     4456897999999999");

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

        List<String> fileLines = List.of(
                "",
                "C4     4456897998199999");

        // When
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(EmptyRowException.class, () -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw LineIdentificationEmptyException with empty line identification")
    void execute_shouldThrowLineIdentificationEmptyException() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String> fileLines = List.of(
                " 3     4456897999999999",
                "C4     4456897998199999");

        // When
        setUp();
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(LineIdentificationEmptyException.class, () -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw BatchNumberEmptyException with null batch number")
    void execute_shouldThrowBatchNumberEmptyException1() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String> fileLines = List.of(
                "C",
                "C4     4456897998199999");

        // When
        setUp();
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(BatchNumberEmptyException.class, () -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw BatchNumberEmptyException with empty batch number")
    void execute_shouldThrowBatchNumberEmptyException2() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String> fileLines = List.of(
                "C      4456897999999999",
                "C3     4456897999999999");

        // When
        setUp();
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(BatchNumberEmptyException.class, () -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw BatchNumberInvalidException with invalid batch number")
    void execute_shouldThrowBatchNumberInvalidException() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String> fileLines = List.of(
                "CC     4456897999999999",
                "C3     4456897999999999");

        // When
        setUp();
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(BatchNumberInvalidException.class, () -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw CardNumberEmptyException with null card number")
    void execute_shouldThrowCardNumberEmptyException1() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String> fileLines = List.of(
                "C1     ",
                "C3     4456897999999999");

        // When
        setUp();
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(CardNumberEmptyException.class, () -> uploadCardUseCase.execute(file));
    }

    @Test
    @DisplayName("Execute should throw CardNumberEmptyException with empty card number")
    void execute_shouldThrowCardNumberEmptyException2() {
        // Given
        MultipartFile file = Mockito.mock(MultipartFile.class);

        List<String> fileLines = List.of(
                "C1                                   5151",
                "C3     4456897999999999");

        // When
        setUp();
        when(readFilePort.execute(file)).thenReturn(fileLines);
        when(file.getOriginalFilename()).thenReturn("teste");

        // Then
        assertThrows(CardNumberEmptyException.class, () -> uploadCardUseCase.execute(file));
    }

    void setUp() {
        when(cardImportProperty.getLineIdentification()).thenReturn(new CardImportRangeProperty(0, 1));
        when(cardImportProperty.getBatchNumber()).thenReturn(new CardImportRangeProperty(1, 7));
        when(cardImportProperty.getCardNumber()).thenReturn(new CardImportRangeProperty(7, 26));
    }

}