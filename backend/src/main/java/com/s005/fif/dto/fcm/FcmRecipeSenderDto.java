package com.s005.fif.dto.fcm;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.dto.response.RecipeStepResponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FcmRecipeSenderDto {
	private Integer type;

	private RecipeResponseDto recipeInfo;

	private List<RecipeStepResponseDto> recipeSteps;

	private Integer sender;

}
