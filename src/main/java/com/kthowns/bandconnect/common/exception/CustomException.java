package com.kthowns.bandconnect.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private String message;
    private CustomErrorCode errorCode;

    public CustomException(CustomErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.errorCode = errorCode;
    }
}
