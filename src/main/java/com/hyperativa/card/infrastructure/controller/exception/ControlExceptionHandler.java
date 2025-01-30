package com.hyperativa.card.infrastructure.controller.exception;

import com.hyperativa.card.domain.exception.*;
import com.hyperativa.card.infrastructure.controller.mapper.ErrorRepresentationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControlExceptionHandler {

    private final ErrorRepresentationMapper errorRepresentationMapper;

    // Errors 4XX
    @ExceptionHandler({
            EmptyRowException.class,
            CardNumberEmptyException.class,
            BatchNumberEmptyException.class,
            BatchNumberInvalidException.class,
            LineIdentificationEmptyException.class
    })
    public ResponseEntity<Object> handleBadRequest(BusinessException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorRepresentationMapper.toRepresentation(exception));
    }

    @ExceptionHandler({ CardDoesNotExistException.class })
    public ResponseEntity<Object> handleNotFound(BusinessException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorRepresentationMapper.toRepresentation(exception));
    }

    @ExceptionHandler({ CardNumberDuplicatedException.class })
    public ResponseEntity<Object> handleConflict(BusinessException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorRepresentationMapper.toRepresentation(exception));
    }

}