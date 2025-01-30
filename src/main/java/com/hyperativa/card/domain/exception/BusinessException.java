package com.hyperativa.card.domain.exception;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class BusinessException extends RuntimeException {

    protected final ErrorCodeEnum code;
    protected final String description;

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getDescription());

        this.code = errorCodeEnum;
        this.description = errorCodeEnum.getDescription();
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum, String description) {
        super(description);

        this.code = errorCodeEnum;
        this.description = description;
    }

}