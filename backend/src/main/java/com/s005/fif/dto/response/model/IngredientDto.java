package com.s005.fif.dto.response.model;

import com.s005.fif.entity.Unit;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IngredientDto {
	private String name;
	private String image;
	private Integer amounts;
	private Unit unit;
}
