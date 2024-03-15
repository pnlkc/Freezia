package com.s005.fif.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeStepResponseDto {
	private Integer recipeStepId;
	private Integer stepNumber;
	private String description;
	private String descriptionWatch;
	private Integer type;
	private String tip;
	private Integer timer;
}
