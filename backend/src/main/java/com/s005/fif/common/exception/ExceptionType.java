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
	TOKEN_EXPIRED(401, "토큰이 만료되었습니다. 다시 로그인 해주세요."),
	TOKEN_NOT_VALID(404, "토큰 형식이 유효하지 않습니다."),
	TOKEN_NOT_EXIST(404, "토큰이 없습니다."),
	MEMBER_ONBOARDED(400, "사용자의 정보가 이미 등록되어 있습니다."),

	// recipe
    RECIPE_NOT_FOUND(404, "레시피를 찾을 수 없습니다."),
    RECIPE_NOT_ACCESSIBLE(403, "본인의 레시피가 아닙니다."),

    // Ingredient
    INGREDIENTS_NOT_FOUND(404, "식재료를 찾을 수 없습니다."),

    // Fridge Ingredient
    FRIDGE_INGREDIENT_NOT_FOUND(404, "냉장고 속 식재료를 찾을 수 없습니다."),

    // FCM
    FCM_REQUEST_FAILED(500, "FCM에 메시지 전송을 실패했습니다."),

    // 장치 연결
    DEVICE_CONNECTION_FAILED(500, "연결된 장치가 존재하지 않습니다."),

    // Disease
    DISEASES_NOT_FOUND(404, "지병을 찾을 수 없습니다."),

	// ShoppingList
	SHOPPING_LIST_UNAUTHORIZED(401, "쇼핑리스트 접근 권한이 없습니다."),
	SHOPPING_LIST_NOT_FOUND(404, "쇼핑리스트를 찾을 수 없습니다."),
	SHOPPING_LIST_EXIST(409, "이미 쇼핑리스트에 등록되어 있습니다."),
    ;

    private final int code;
    private final String msg;

    ExceptionType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
