package com.hyperativa.card.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    CAR001("The card number is already in use"),
    CAR002("The card number does not exist"),
    CAR003("The file could not be read"),
    CAR004("The row %s is empty"),
    CAR005("The row %s does not have a card number");

    private final String description;

}