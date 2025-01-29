package com.hyperativa.card.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    CAR001("The card number is already in use");

    private final String description;

}