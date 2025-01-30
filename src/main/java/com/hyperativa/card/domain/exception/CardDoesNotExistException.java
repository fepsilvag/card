package com.hyperativa.card.domain.exception;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class CardDoesNotExistException extends BusinessException {

    public CardDoesNotExistException() {
        super(ErrorCodeEnum.CAR002);
    }

}