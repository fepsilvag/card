package com.hyperativa.card.infrastructure.controller;

import com.hyperativa.card.application.dto.CardInbound;
import com.hyperativa.card.application.dto.CardOutbound;
import com.hyperativa.card.domain.exception.CardDoesNotExistException;
import com.hyperativa.card.domain.port.in.CreateCardPort;
import com.hyperativa.card.domain.port.in.SearchCardPort;
import com.hyperativa.card.infrastructure.controller.mapper.CardRepresentationMapper;
import com.hyperativa.openapi.api.CardsApi;
import com.hyperativa.representation.CardIdResponseRepresentation;
import com.hyperativa.representation.CardRequestRepresentation;
import com.hyperativa.representation.CardResponseRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CardController implements CardsApi {

    private final CreateCardPort createCardPort;
    private final SearchCardPort searchCardPort;
    private final CardRepresentationMapper cardRepresentationMapper;

    @Override
    public ResponseEntity<CardResponseRepresentation> createCard(CardRequestRepresentation createCardRequestRepresentation) {
        log.info("Received request to create card: {}", createCardRequestRepresentation);
        CardInbound inbound = cardRepresentationMapper.toInbound(createCardRequestRepresentation);
        CardOutbound outbound = createCardPort.execute(inbound);
        CardResponseRepresentation representation = cardRepresentationMapper.toRepresentation(outbound);

        log.info("Returning response of created card: {}", representation);
        return ResponseEntity.status(HttpStatus.CREATED).body(representation);
    }

    @Override
    public ResponseEntity<CardIdResponseRepresentation> searchCard(String cardNumber) {
        log.info("Received request to search card: {}", cardNumber);
        CardIdResponseRepresentation representation = Optional.ofNullable(searchCardPort.execute(cardNumber))
                .map(cardRepresentationMapper::toIdRepresentation)
                .orElseThrow(CardDoesNotExistException::new);

        log.info("Returning response of searched card: {}", representation);
        return ResponseEntity.ok(representation);
    }

}