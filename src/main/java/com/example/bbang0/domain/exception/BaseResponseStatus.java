package com.example.bbang0.domain.exception;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /*
     * 2000 : request error
     * */
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    EXIST_EMAIL(false, 2004, "가입된 이메일입니다."),
    EXIST_ID(false, 2005, "이미 존재하는 아이디입니다."),
    INVALID_EMAIL(false,2006,"존재하지 않는 아이디입니다"),
    INVALID_PASSWORD(false,2007,"비밀번호가 일치하지 않습니다"),

    FAIL_REVIEW_UPDATE(false,2008,"리뷰 수정에 실패했습니다."),
    FAIL_REVIEW_DELETE(false,2009,"리뷰 삭제에 실패했습니다."),
    INVALID_BAKERY(false, 2010, "해당 베이커리가 아닙니다."),
    USER_NO_BAKERY(false, 2010, "등록된 베이커리가 없습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
