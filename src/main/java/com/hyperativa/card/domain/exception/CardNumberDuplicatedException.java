package com.hyperativa.card.domain.exception;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class CardNumberDuplicatedException extends BusinessException {

    public CardNumberDuplicatedException() {
        super(ErrorCodeEnum.CAR001);
    }

}