package com.s005.fif.dto.response;

import java.util.List;

import com.s005.fif.entity.Member;
import com.s005.fif.entity.Recipe;
import com.s005.fif.entity.RecipeStep;
import com.s005.fif.entity.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor
@ToString
@Slf4j
public class GeneAIResponseRecipeDto {
	private String reply;
	private List<GeneAIRecipeDto> recipeList;

	@Getter
	@NoArgsConstructor
	@ToString
	public static class GeneAIRecipeDto {
		private String name;
		private List<GeneAIIngredientDto> ingredientList;
		private List<GeneAISeasoningDto> seasoningList;
		private String cookTime;
		private String calorie;
		private String servings;
		private String recipeType; // ex) 볶음 요리
		private List<GeneAIRecipeStepDto> recipeSteps;

		public static Recipe toEntity(
			GeneAIRecipeDto r,
			Member member,
			String imgUrl,
			String recommendDesc,
			Integer recommendType
		) {
			List<String> ingredientList = r.getIngredientList().stream().map((ingredient) -> ingredient.getName() + ":" + ingredient.getAmounts() + ":" + ingredient.getUnit()).toList();
			List<String> seasoningList = r.getSeasoningList().stream().map((seasoning) -> seasoning.getName() + ":" + seasoning.getAmounts() + ":" + seasoning.getUnit()).toList();
			return Recipe.builder()
				.member(member)
				.name(r.getName())
				.cookTime(Util.extractDigit(r.getCookTime()))
				.calorie(Util.extractDigit(r.getCalorie()))
				.ingredientList(String.join(",", ingredientList))
				.seasoningList(String.join(",", seasoningList))
				.imgUrl(imgUrl)
				.saveYn(false)
				.completeYn(false)
				.recommendType(recommendType)
				.recommendDesc(recommendDesc)
				.recipeTypes(r.recipeType)
				.serving(1)
				.build();
		}
	}

	@Getter
	@NoArgsConstructor
	@ToString
	public static class GeneAIIngredientDto {
		private String name;
		private String amounts;
		private String unit;

	}

	@Getter
	@NoArgsConstructor
	@ToString
	public static class GeneAISeasoningDto {
		private String name;
		private String amounts;
		private String unit;

	}

	@Getter
	@NoArgsConstructor
	@ToString
	public static class GeneAIRecipeStepDto {
		private String type;
		private String description;
		private String name;
		private String duration;
		private String tip;
		private String timer;

		public static RecipeStep toEntity(GeneAIRecipeStepDto rStep, Recipe recipe, int stepNumber) {
			return RecipeStep.builder()
				.recipe(recipe)
				.stepNumber(stepNumber)
				.description(rStep.getDescription())
				.type(Type.from(rStep.type))
				.tip(rStep.tip)
				.timer((Util.extractDigit(rStep.getTimer())) * 60)
				.build();
		}
	}
}

class Util {
	static int extractDigit(String time) {
		if (time == null)
			return 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < time.length(); i++) {
			char c = time.charAt(i);
			if (!Character.isDigit(c))
				break;
			sb.append(c);
		}
		return !sb.isEmpty() ? Integer.parseInt(sb.toString()) : 0;
	}
}
