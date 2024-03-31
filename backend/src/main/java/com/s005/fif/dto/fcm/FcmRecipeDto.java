package com.s005.fif.dto.fcm;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.dto.response.RecipeStepResponseDto;
import com.s005.fif.dto.response.model.IngredientDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FcmRecipeDto {
	private Integer type;

	private RecipeResponseDto recipeInfo;

	private List<RecipeStepResponseDto> recipeSteps;

}
