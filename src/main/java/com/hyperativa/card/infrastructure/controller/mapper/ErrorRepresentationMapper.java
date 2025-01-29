package com.hyperativa.card.infrastructure.controller.mapper;

import com.hyperativa.card.domain.exception.BusinessException;
import com.hyperativa.representation.ErrorResponseRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ErrorRepresentationMapper {

    ErrorResponseRepresentation toRepresentation(BusinessException businessException);

}