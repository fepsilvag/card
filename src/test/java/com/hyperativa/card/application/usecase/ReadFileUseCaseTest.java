package com.hyperativa.card.application.usecase;

import com.hyperativa.card.infrastructure.config.property.FileReaderProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class ReadFileUseCaseTest {

    @Mock
    private FileReaderProperty fileReaderProperty;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ReadFileUseCase readFileUseCase;

    @Test
    @DisplayName("Execute should process file")
    void execute_shouldProcessFile() throws Exception {
        // Given
        String content = "a\nb";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());

        // When
        when(fileReaderProperty.getBatchSize()).thenReturn(10);
        when(file.getInputStream()).thenReturn(inputStream);
        when(file.getOriginalFilename()).thenReturn("testfile.txt");

        // Then
        List<String> result = readFileUseCase.execute(file);

        assertEquals(2, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));

        verify(file, times(1)).getInputStream();
    }

    @Test
    @DisplayName("Execute should throw RuntimeException")
    void execute_shouldThrowRuntimeException() throws IOException {
        // When
        when(file.getInputStream()).thenThrow(new IOException());

        // When
        assertThrows(RuntimeException.class, () -> readFileUseCase.execute(file));
    }

}