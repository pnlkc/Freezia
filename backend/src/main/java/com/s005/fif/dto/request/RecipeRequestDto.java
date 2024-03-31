package com.s005.fif.dto.request;

import java.util.List;

import com.s005.fif.dto.response.model.IngredientDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeRequestDto {
	private Integer recipeId;
	private String name;
	private Integer cookTime;
	private Integer calorie;
	private List<IngredientDto> ingredientList;
	private List<IngredientDto> seasoningList;
	private String imgUrl;
	// private Boolean saveYn;
	// private Boolean completeYn;
	private Integer recommendType;
	private String recipeTypes;
	private Integer serving;
}
