package com.hyperativa.card.domain.exception;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class BatchNumberInvalidException extends BusinessException {

    public BatchNumberInvalidException(Integer row, String lineIdentification) {
        super(ErrorCodeEnum.CAR007, String.format(ErrorCodeEnum.CAR007.getDescription(), row, lineIdentification));
    }

}