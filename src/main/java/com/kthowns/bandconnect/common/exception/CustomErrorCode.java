package com.kthowns.bandconnect.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    LOGIN_FAILED("아이디 또는 비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED),
    DUPLICATED_EMAIL("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    DUPLICATED_USERNAME("이미 사용 중인 아이디입니다.", HttpStatus.CONFLICT),
    INTERNAL_SERVER_ERROR("알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;
    private final HttpStatus status;
}
