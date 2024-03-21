package com.s005.fif.common.types;

import lombok.Getter;

@Getter
public enum HealthRecipeType {
	STRESS("stress"), SLEEP("sleep"), BLOOD_OXYGEN("blood-oxygen");

	private final String path;

	HealthRecipeType(String path) {
		this.path = path;
	}

}
