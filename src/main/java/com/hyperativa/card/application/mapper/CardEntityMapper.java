package com.hyperativa.card.application.mapper;

import com.hyperativa.card.application.dto.CardInbound;
import com.hyperativa.card.application.dto.CardOutbound;
import com.hyperativa.card.domain.domain.CardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardEntityMapper {

    CardEntity toEntity(CardInbound cardInbound);
    CardOutbound toOutbound(CardEntity cardEntity);

}