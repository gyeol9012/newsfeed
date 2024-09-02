package com.sparta.newsfeed19.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    // 공통 응답 코드
    SUCCESS("S001", "정상 처리되었습니다.", HttpStatus.OK),

    // 사용자 응답 코드
    USER_NOT_FOUND("U001", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("U002", "이미 존재하는 사용자입니다.", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS("U003", "이미 존재하는 이메일입니다.", HttpStatus.CONFLICT),
    INVALID_PASSWORD("U004", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),

    // 게시물 응답 코드
    POST_NOT_FOUND("P001", "게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_CREATION_FAILED("P002", "게시물 생성에 실패했습니다.", HttpStatus.BAD_REQUEST),
    POST_UPDATE_FAILED("P003", "게시물 수정에 실패했습니다.", HttpStatus.BAD_REQUEST),

    // 댓글 응답 코드
    COMMENT_NOT_FOUND("C001", "댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    COMMENT_CREATION_FAILED("C002", "댓글 생성에 실패했습니다.", HttpStatus.BAD_REQUEST),

    // 팔로우 응답 코드
    FOLLOW_NOT_FOUND("F001", "팔로우 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FOLLOW_CREATION_FAILED("F002", "팔로우 생성에 실패했습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ResponseCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
