package com.s005.fif.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class GeneAIResponseRecipeDto {
	private String reply;
	private List<Recipe> recipeList;

	@Getter
	@NoArgsConstructor
	@ToString
	public static class Recipe {
		private String name;
		private List<Ingredient> ingredientList;
		private List<Seasoning> seasoningList;
		private String cookTime;
		private String calorie;
		private String servings;
		private String recipeType;
		private List<RecipeStep> recipeSteps;

	}

	@Getter
	@NoArgsConstructor
	@ToString
	public static class Ingredient {
		private String name;
		private String amounts;
		private String unit;

	}

	@Getter
	@NoArgsConstructor
	@ToString
	public static class Seasoning {
		private String name;
		private String amounts;
		private String unit;

	}

	@Getter
	@NoArgsConstructor
	@ToString
	public static class RecipeStep {
		private String type;
		private String description;
		private String name;
		private String duration;
		private String tip;
		private String timer;

	}
}
