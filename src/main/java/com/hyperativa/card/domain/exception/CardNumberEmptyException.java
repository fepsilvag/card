package com.hyperativa.card.domain.exception;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class CardNumberEmptyException extends BusinessException {

    public CardNumberEmptyException(String rowIdentification) {
        super(ErrorCodeEnum.CAR008, String.format(ErrorCodeEnum.CAR008.getDescription(), rowIdentification));
    }

}