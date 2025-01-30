package com.hyperativa.card.domain.exception;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class CardNumberMandatoryException extends BusinessException {

    public CardNumberMandatoryException(Integer row) {
        super(ErrorCodeEnum.CAR005, String.format(ErrorCodeEnum.CAR005.getDescription(), row));
    }

}