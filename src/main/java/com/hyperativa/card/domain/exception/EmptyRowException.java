package com.hyperativa.card.domain.exception;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class EmptyRowException extends BusinessException {

    public EmptyRowException(Integer row) {
        super(ErrorCodeEnum.CAR004, String.format(ErrorCodeEnum.CAR004.getDescription(), row));
    }

}