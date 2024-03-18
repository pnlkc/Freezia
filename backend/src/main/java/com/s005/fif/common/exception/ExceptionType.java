package com.s005.fif.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {

    // test
    TEST_400(400, "400 에러"),
    TEST_500(500, "500 에러"),

    // fridge
    FRIDGE_NOT_FOUND(404, "냉장고를 찾을 수 없습니다."),

    // member
    MEMBER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),

    // recipe
    RECIPE_NOT_FOUND(404, "레시피를 찾을 수 없습니다."),
    RECIPE_NOT_ACCESSIBLE(403, "본인의 레시피가 아닙니다."),

    // Ingredient
    INGREDIENTS_NOT_FOUND(404, "식재료를 찾을 수 없습니다."),

    // FCM
    FCM_REQUEST_FAILED(500, "FCM에 메시지 전송을 실패했습니다."),
    ;

    private final int code;
    private final String msg;

    ExceptionType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
