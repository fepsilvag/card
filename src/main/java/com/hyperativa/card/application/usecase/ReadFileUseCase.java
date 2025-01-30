package com.hyperativa.card.application.usecase;

import com.hyperativa.card.domain.port.in.ReadFilePort;
import com.hyperativa.card.infrastructure.config.property.FileReaderProperty;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class ReadFileUseCase implements ReadFilePort {

    private final FileReaderProperty fileReaderProperty;

    @Override
    @Transactional
    public List<String> execute(MultipartFile file) {
        log.info("Starting file processing: {}", file.getOriginalFilename());

        try (
                InputStream inputStream = file.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            List<String> lines = getLines(bufferedReader);
            log.info("File processing completed: {}", file.getOriginalFilename());
            return lines;
        } catch (Exception e) {
            log.error("Error processing file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException(e);
        }
    }

    private List<String> getLines(BufferedReader bufferedReader) {
        return bufferedReader.lines()
                .limit(fileReaderProperty.getBatchSize())
                .toList();
    }

}