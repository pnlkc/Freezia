package com.s005.fif.entity;

public enum Type {
	BOIL("끓이기", 0),
	ROAST("굽기", 1),
	PUT("넣기", 2),
	DONE("완성", 999),
	;

	private final String title;
	private final int code;

	Type (String title, int code) {
		this.title = title;
		this.code = code;
	}
}
