package com.s005.fif.common.types;

public enum RecipeType {
	HANSIK("한식"),
	YANGSIK("양식"),
	JUNGSIK("중식"),
	ILSIK("일식"),
	MITBANCHAN("밑반찬"),
	MYEONYORI("면요리"),
	BOKKEUMYORI("볶음요리"),
	JJIMYORI("찜요리"),
	GUKMULYORI("국물요리"),
	YUTONGGIHAN("유통기한 임박");

	private final String type;

	RecipeType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
