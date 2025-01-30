package com.hyperativa.card.application.usecase;

import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.exception.*;
import com.hyperativa.card.domain.port.in.ReadFilePort;
import com.hyperativa.card.domain.port.in.UploadCardPort;
import com.hyperativa.card.domain.port.out.FindCardByNumberPort;
import com.hyperativa.card.domain.port.out.SaveCardPort;
import com.hyperativa.card.infrastructure.config.property.CardImportProperty;
import com.hyperativa.card.infrastructure.config.property.CardImportRangeProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UploadCardUseCase implements UploadCardPort {

    private final ReadFilePort readFilePort;
    private final SaveCardPort saveCardPort;
    private final CardImportProperty cardImportProperty;
    private final FindCardByNumberPort findCardByNumberPort;

    @Override
    @Transactional
    public void execute(MultipartFile file) {
        List<String> fileLines = readFilePort.execute(file);

        log.info("Saving cards from file: {}", file.getOriginalFilename());
        for (int i = 0; i < fileLines.size(); i++) {
            saveCard(fileLines.get(i), i);
        }
    }

    private void saveCard(String line, int index) {
        validateItem(line, index);

        String cardNumber = extractSubstringIfPresent(line, cardImportProperty.getCardNumber());
        if (findCardByNumberPort.execute(cardNumber) == null) {
            saveCardPort.execute(CardEntity.builder()
                    .cardNumber(cardNumber)
                    .build());
        }
    }

    private void validateItem(String line, int index) {
        if (line == null || line.trim().isEmpty()) {
            log.error("Row {} is empty", (index + 1));
            throw new EmptyRowException(index + 1);
        }

        String lineIdentification = extractSubstringIfPresent(line, cardImportProperty.getLineIdentification());
        String batch = extractSubstringIfPresent(line, cardImportProperty.getBatchNumber());
        String card = extractSubstringIfPresent(line, cardImportProperty.getCardNumber());

        validateLineIdentification(lineIdentification, index);
        validateBatch(lineIdentification, batch, index);
        validateCard(lineIdentification, batch, card, index);
    }

    private String extractSubstringIfPresent(String line, CardImportRangeProperty range) {
        if (line.length() <= range.getBegin()) {
            return null;
        }

        Integer end = range.getEnd();
        if (line.length() < end) {
            end = line.length();
        }

        return line.substring(range.getBegin(), end).trim();
    }

    private void validateLineIdentification(String lineIdentification, int index) {
        if (lineIdentification.trim().isEmpty()) {
            log.error("Row {} does not have a line identification", (index + 1));
            throw new LineIdentificationEmptyException(index + 1);
        }
    }

    private void validateBatch(String lineIdentification, String batch, int index) {
        if (batch == null || batch.trim().isEmpty()) {
            log.error("Row {}, identification {} does not have a batch number", (index + 1), lineIdentification);
            throw new BatchNumberEmptyException(index + 1, lineIdentification);
        }

        try {
            Integer.parseInt(batch);
        } catch (NumberFormatException e) {
            log.error("Row {}, identification {} has an invalid batch number", (index + 1), lineIdentification);
            throw new BatchNumberInvalidException(index + 1, lineIdentification);
        }
    }

    private void validateCard(String lineIdentification, String batch, String card, int index) {
        int batchNumber = Integer.parseInt(batch);

        if (card == null || card.trim().isEmpty()) {
            log.error("Row {} does not have a card number", lineIdentification + batchNumber);
            throw new CardNumberEmptyException(lineIdentification + batchNumber);
        }
    }

}