package com.hyperativa.card.domain.exception;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class FileReadException extends BusinessException {

    public FileReadException() {
        super(ErrorCodeEnum.CAR003);
    }
}