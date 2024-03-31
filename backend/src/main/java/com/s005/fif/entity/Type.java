package com.s005.fif.entity;

import lombok.Getter;

@Getter
public enum Type {
	PREPARE("재료 손질", 0),
	COOK("조리", 1),
	FINISH("마무리", 2),
	;

	private final String title;
	private final int code;

	Type (String title, int code) {
		this.title = title;
		this.code = code;
	}

	public static Type from(String title) {
		Type type = Type.values()[0];
		for (Type t : Type.values()) {
			if (t.title.equals(title)) {
				type = t;
				break;
			}
		}
		return type;
	}
}
