package com.kthowns.bandconnect.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private String message;
    private CustomResponseCode errorCode;

    public CustomException(CustomResponseCode errorCode) {
        this.message = errorCode.getMessage();
        this.errorCode = errorCode;
    }
}
