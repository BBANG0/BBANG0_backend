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
    EXIST_EMAIL(false, 2000, "가입된 이메일입니다."),
    EXIST_ID(false, 2001, "이미 존재하는 아이디입니다."),
    INVALID_EMAIL(false,2002,"존재하지 않는 아이디입니다"),
    INVALID_PASSWORD(false,2003,"비밀번호가 일치하지 않습니다"),

    // bakery
    BAKERYS_EMPTY_BAKERY_ID(false, 2004, "존재하지 않는 빵집입니다."),

    // bread
    BREADS_EMPTY_BREAD_ID(false, 2005, "존재하지 않는 빵입니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),

    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),


    /**
     * 5000 : 수정 오류
     */
    MODIFY_FAIL_BREAD(false, 5001, "빵 정보 수정에 실패하였습니다."),


    /**
     * 6000 : 삭제 오류
     */
    DELETE_FAIL_BREAD(false, 6001, "빵 삭제에 실패하였습니다.");




    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
