package com.kthowns.bandconnect.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomResponseCode {
    // Success
    SIGNUP_SUCCESS("회원가입이 성공했습니다.", HttpStatus.OK),
    APPLY_SUCCESS("지원이 성공했습니다.", HttpStatus.OK),
    COMMENT_ADD_SUCCESS("댓글이 등록되었습니다.", HttpStatus.OK),
    COMMENT_DELETE_SUCCESS("댓글이 삭제되었습니다.", HttpStatus.OK),
    POST_DELETE_SUCCESS("게시글이 삭제되었습니다.", HttpStatus.OK),
    POST_ADD_SUCCESS("게시글이 등록되었습니다.", HttpStatus.OK),

    // Fail
    APPLY_RECURSIVE_ERROR("자신의 구인글에는 지원할 수 없습니다.", HttpStatus.BAD_REQUEST),

    BAND_NOT_FOUND("해당하는 밴드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_NOT_FOUND("해당하는 포스트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND("해당하는 댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    RECRUIT_NOT_FOUND("해당하는 구인을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    LOGIN_FAILED("아이디 또는 비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED),
    DUPLICATED_EMAIL("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    DUPLICATED_USERNAME("이미 사용 중인 아이디입니다.", HttpStatus.CONFLICT),
    DUPLICATED_BAND("이미 사용 중인 밴드명 입니다.", HttpStatus.CONFLICT),

    FORBIDDEN("허용되지 않은 접근입니다.", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;
    private final HttpStatus status;

    @Override
    public String toString() {
        return message;
    }
}
