package com.hyperativa.card.infrastructure.controller.exception;

import com.hyperativa.card.domain.exception.CardDoesNotExistException;
import com.hyperativa.card.domain.exception.CardNumberDuplicatedException;
import com.hyperativa.card.infrastructure.controller.mapper.ErrorRepresentationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class ControlExceptionHandler {

    private final ErrorRepresentationMapper errorRepresentationMapper;

    // Errors 4XX
    @ExceptionHandler({ CardDoesNotExistException.class })
    public ResponseEntity<Object> handle(CardDoesNotExistException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorRepresentationMapper.toRepresentation(exception));
    }

    @ExceptionHandler({ CardNumberDuplicatedException.class })
    public ResponseEntity<Object> handle(CardNumberDuplicatedException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorRepresentationMapper.toRepresentation(exception));
    }

}