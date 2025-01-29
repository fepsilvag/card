package com.hyperativa.card.infrastructure.controller;

import com.hyperativa.card.application.dto.CardInbound;
import com.hyperativa.card.application.dto.CardOutbound;
import com.hyperativa.card.domain.port.in.CreateCardPort;
import com.hyperativa.card.domain.port.in.SearchCardPort;
import com.hyperativa.card.infrastructure.controller.mapper.CardRepresentationMapper;
import com.hyperativa.openapi.api.CardsApi;
import com.hyperativa.representation.CardIdResponseRepresentation;
import com.hyperativa.representation.CardRequestRepresentation;
import com.hyperativa.representation.CardResponseRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequiredArgsConstructor
public class CardController implements CardsApi {

    private final CreateCardPort createCardPort;
    private final SearchCardPort searchCardPort;
    private final CardRepresentationMapper cardRepresentationMapper;

    @Override
    public ResponseEntity<CardResponseRepresentation> createCard(CardRequestRepresentation createCardRequestRepresentation) {
        log.info("Received request to create card: " + createCardRequestRepresentation);
        CardInbound cardInbound = cardRepresentationMapper.toInbound(createCardRequestRepresentation);
        CardOutbound CardOutbound = createCardPort.execute(cardInbound);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardRepresentationMapper.toRepresentation(CardOutbound));
    }

    @Override
    public ResponseEntity<CardIdResponseRepresentation> searchCard(String cardNumber) {
        log.info("Received request to search card: " + cardNumber);
        CardOutbound cardOutbound = searchCardPort.execute(cardNumber);

        return ResponseEntity.ok(cardRepresentationMapper.toIdRepresentation(cardOutbound));
    }

}