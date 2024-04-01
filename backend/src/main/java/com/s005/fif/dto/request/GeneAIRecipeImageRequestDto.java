package com.s005.fif.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeneAIRecipeImageRequestDto {
	@NotNull
	Integer recipeId;
	@NotNull
	String recipeName;
}
