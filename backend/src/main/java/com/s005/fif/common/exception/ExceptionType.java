package com.s005.fif.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {

    // test
    TEST_400(400, "400 에러"),
    TEST_500(500, "500 에러"),
    ;

    private final int code;
    private final String msg;

    ExceptionType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
