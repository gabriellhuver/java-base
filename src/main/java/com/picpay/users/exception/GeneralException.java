package com.picpay.users.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class GeneralException {

    private String code;
    private String message;

    public GeneralException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
