package com.hyperativa.card.infrastructure.controller.mapper;

import com.hyperativa.card.application.dto.CardInbound;
import com.hyperativa.card.application.dto.CardOutbound;
import com.hyperativa.representation.CardIdResponseRepresentation;
import com.hyperativa.representation.CardRequestRepresentation;
import com.hyperativa.representation.CardResponseRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardRepresentationMapper {

    CardResponseRepresentation toRepresentation(CardOutbound cardOutbound);
    CardInbound toInbound(CardRequestRepresentation cardRequestRepresentation);
    CardIdResponseRepresentation toIdRepresentation(CardOutbound cardOutbound);

}