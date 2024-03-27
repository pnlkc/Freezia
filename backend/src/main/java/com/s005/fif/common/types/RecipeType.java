package com.s005.fif.common.types;

import lombok.Getter;

@Getter
public enum RecipeType {
	STRESS(1, "stress"),
	SLEEP(2, "sleep"),
	BLOOD_OXYGEN(3, "blood-oxygen");

	private final int number;
	private final String name;

	RecipeType(int number, String path) {
		this.number = number;
		this.name = path;
	}

}
