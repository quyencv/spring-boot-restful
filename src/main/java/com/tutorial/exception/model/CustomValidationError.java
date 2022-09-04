package com.tutorial.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class CustomValidationError extends CustomSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    CustomValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}