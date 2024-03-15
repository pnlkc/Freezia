package com.s005.fif.dto.response.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IngredientDto {
	private String name;
	private String image;
	private String amounts;
	private String unit;
}
