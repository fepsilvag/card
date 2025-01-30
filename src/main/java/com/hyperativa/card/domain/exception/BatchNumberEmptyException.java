package com.hyperativa.card.domain.exception;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class BatchNumberEmptyException extends BusinessException {

    public BatchNumberEmptyException(Integer row, String lineIdentification) {
        super(ErrorCodeEnum.CAR006, String.format(ErrorCodeEnum.CAR006.getDescription(), row, lineIdentification));
    }

}