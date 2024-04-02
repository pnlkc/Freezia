package com.s005.fif.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.s005.fif.dto.response.model.IngredientDto;
import com.s005.fif.entity.Recipe;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeRecommendationResponseDto {
	private Integer recipeId;
	private String name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate createDate;
	private Integer cookTime;
	private Integer calorie;
	private List<IngredientDto> ingredientList;
	private List<IngredientDto> seasoningList;
	private String imgUrl;
	private Boolean saveYn;
	private Boolean completeYn;
	private Integer recommendType;
	private String recipeTypes;
	private Integer serving;
	private String recommendDesc;

	public static RecipeRecommendationResponseDto fromEntity(Recipe recipe, List<IngredientDto> ingredientList,
		List<IngredientDto> seasoningList) {
		return RecipeRecommendationResponseDto
			.builder()
			.recipeId(recipe.getRecipeId())
			.name(recipe.getName())
			.createDate(recipe.getCreateDate())
			.cookTime(recipe.getCookTime())
			.calorie(recipe.getCalorie())
			.ingredientList(ingredientList)
			.seasoningList(seasoningList)
			.imgUrl(recipe.getImgUrl())
			.saveYn(recipe.getSaveYn())
			.completeYn(recipe.getCompleteYn())
			.recommendType(recipe.getRecommendType())
			.recipeTypes(recipe.getRecipeTypes())
			.serving(recipe.getServing())
			.recommendDesc(recipe.getRecommendDesc())
			.build();
	}
}
