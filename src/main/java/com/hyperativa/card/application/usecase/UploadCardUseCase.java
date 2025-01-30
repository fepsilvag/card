package com.hyperativa.card.application.usecase;

import com.hyperativa.card.domain.domain.CardEntity;
import com.hyperativa.card.domain.exception.CardNumberMandatoryException;
import com.hyperativa.card.domain.exception.EmptyRowException;
import com.hyperativa.card.domain.port.in.ReadFilePort;
import com.hyperativa.card.domain.port.in.UploadCardPort;
import com.hyperativa.card.domain.port.out.SaveCardPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UploadCardUseCase implements UploadCardPort {

    private final ReadFilePort readFilePort;
    private final SaveCardPort saveCardPort;

    @Override
    @Transactional
    public void execute(MultipartFile file) {
        List<String[]> fileLines = readFilePort.execute(file);

        log.info("Saving cards from file: {}", file.getOriginalFilename());
        for (int i = 0; i < fileLines.size(); i++) {
            saveCard(fileLines.get(i), i);
        }
    }

    private void saveCard(String[] line, int index) {
        validateItem(line, index);

        CardEntity entity = CardEntity.builder()
                .cardNumber(line[0])
                .build();

        saveCardPort.execute(entity);
    }

    private void validateItem(String[] line, int index) {
        if (line == null || line.length == 0) {
            log.error("Row {} is empty", (index + 1));
            throw new EmptyRowException(index + 1);
        } else if (line[0] == null || line[0].trim().isEmpty()) {
            log.error("Card number is mandatory");
            throw new CardNumberMandatoryException(index + 1);
        }
    }

}