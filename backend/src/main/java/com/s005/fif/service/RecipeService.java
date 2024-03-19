package com.s005.fif.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s005.fif.common.Constant;
import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.request.CompleteCookRequestDto;
import com.s005.fif.dto.request.model.AddIngredientDto;
import com.s005.fif.dto.request.model.RemoveIngredientDto;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.dto.response.RecipeStepResponseDto;
import com.s005.fif.dto.response.model.IngredientDto;
import com.s005.fif.entity.CompleteCook;
import com.s005.fif.entity.Ingredient;
import com.s005.fif.entity.Recipe;
import com.s005.fif.entity.RecipeStep;
import com.s005.fif.repository.CompleteCookRepository;
import com.s005.fif.repository.IngredientRepository;
import com.s005.fif.repository.RecipeRepository;
import com.s005.fif.repository.RecipeStepRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final IngredientRepository ingredientRepository;
	private final RecipeStepRepository recipeStepRepository;
	private final CompleteCookRepository completeCookRepository;

	/**
	 * 레시피 세부 정보를 반환합니다.
	 * @param memberId 사용자 ID
	 * @param recipeId 레시피 ID
	 * @return 레시피 세부 정보
	 */
	public RecipeResponseDto getRecipe(Integer memberId, Integer recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new CustomException(ExceptionType.RECIPE_NOT_FOUND));

		// [예외 처리] 본인의 레시피가 아닐 경우
		if (!recipe.getMember().getMemberId().equals(memberId)) {
			throw new CustomException(ExceptionType.RECIPE_NOT_ACCESSIBLE);
		}

		List<IngredientDto> ingredientList = new ArrayList<>();
		List<IngredientDto> seasoningList = new ArrayList<>();
		String[] ingredients = (recipe.getIngredientList() + "," + recipe.getSeasoningList()).split(",");
		for (String ingredient : ingredients) {
			String[] str = ingredient.split(":");
			String name = str[0];
			String amounts = str[1];
			String unit = str[2];

			Optional<Ingredient> findIngredientOpt = ingredientRepository.findByName(name);

			// 식재료 DB에 없는 경우
			if (findIngredientOpt.isEmpty()) {
				/*
				식재료 DB에 추가
				DB에서 imgUrl, seasoningYn 직접 수정 필요함
				 */
				ingredientRepository.save(Ingredient.builder()
					.name(name)
					.imgUrl(Constant.DEFAULT_INGREDIENT_IMG_URL)
					.seasoningYn(true)	// 양념으로 취급
					.expirationPeriod(Constant.DEFAULT_INGREDIENT_EXPIRATION_PERIOD)
					.build());
				seasoningList.add(IngredientDto.builder()
					.name(name)
					.amounts(amounts)
					.unit(unit)
					.build());
			}
			// 식재료 DB에 있는 경우
			else {
				Ingredient findIngredient = findIngredientOpt.get();
				IngredientDto ingredientDto = IngredientDto.builder()
					.name(name)
					.image(findIngredient.getImgUrl())
					.amounts(amounts)
					.unit(unit)
					.build();

				// 식재료인 경우
				if (!findIngredient.getSeasoningYn()) {
					ingredientList.add(ingredientDto);
				}
				// 양념인 경우
				else {
					seasoningList.add(ingredientDto);
				}
			}
		}

		return RecipeResponseDto.builder()
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
			.build();
	}

	/**
	 * 레시피의 모든 단계를 반환합니다.
	 * @param memberId 사용자 ID
	 * @param recipeId 레시피 ID
	 * @return 레시피 단계로 이루어진 List
	 */
	@Transactional(readOnly = true)
	public List<RecipeStepResponseDto> getRecipeSteps(Integer memberId, Integer recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new CustomException(ExceptionType.RECIPE_NOT_FOUND));

		// [예외 처리] 본인의 레시피가 아닐 경우
		if (!recipe.getMember().getMemberId().equals(memberId)) {
			throw new CustomException(ExceptionType.RECIPE_NOT_ACCESSIBLE);
		}

		List<RecipeStep> recipeStepList = recipeStepRepository.findByRecipeOrderByStepNumberAsc(recipe);

		return recipeStepList.stream()
			.map(recipeStep -> RecipeStepResponseDto.builder()
				.recipeStepId(recipeStep.getRecipeStepId())
				.stepNumber(recipeStep.getStepNumber())
				.description(recipeStep.getDescription())
				.descriptionWatch(recipeStep.getType().getTitle())
				.type(recipeStep.getType().getCode())
				.tip(recipeStep.getTip())
				.timer(recipeStep.getTimer())
				.build())
			.toList();
	}

	/**
	 * 레시피 저장 여부를 토글합니다.
	 * @param memberId 사용자 ID
	 * @param recipeId 레시피 ID
	 * @return 완료 메세지
	 */
	public String toggleSaveYn(Integer memberId, Integer recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new CustomException(ExceptionType.RECIPE_NOT_FOUND));

		// [예외 처리] 본인의 레시피가 아닐 경우
		if (!recipe.getMember().getMemberId().equals(memberId)) {
			throw new CustomException(ExceptionType.RECIPE_NOT_ACCESSIBLE);
		}

		recipe.toggleSaveYn();

		return recipe.getSaveYn() ? "레시피가 저장되었습니다." : "레시피가 저장 취소되었습니다.";
	}

	/**
	 * 요리 기록을 추가합니다.
	 * @param memberId 사용자 ID
	 * @param recipeId 레시피 ID
	 * @param dto dto
	 * @return 완료 메세지
	 */
	public String completeCook(Integer memberId, Integer recipeId, CompleteCookRequestDto dto) {
		Recipe recipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new CustomException(ExceptionType.RECIPE_NOT_FOUND));

		// [예외 처리] 본인의 레시피가 아닐 경우
		if (!recipe.getMember().getMemberId().equals(memberId)) {
			throw new CustomException(ExceptionType.RECIPE_NOT_ACCESSIBLE);
		}

		// 추가한 식재료 stringify
		StringBuilder addIngredient = new StringBuilder();
		for (AddIngredientDto ingredient : dto.getAddIngredients()) {
			addIngredient.append(ingredient.getName())
				.append(":")
				.append(ingredient.getAmounts())
				.append(":")
				.append(ingredient.getUnit())
				.append(",");
		}
		if (!addIngredient.isEmpty()) {
			addIngredient.deleteCharAt(addIngredient.length() - 1);
		}

		// 제외한 식재료 stringify
		StringBuilder removeIngredient = new StringBuilder();
		for (RemoveIngredientDto ingredient : dto.getRemoveIngredients()) {
			removeIngredient.append(ingredient.getName())
				.append(",");
		}
		if (!removeIngredient.isEmpty()) {
			removeIngredient.deleteCharAt(removeIngredient.length() - 1);
		}

		completeCookRepository.save(CompleteCook.builder()
			.recipe(recipe)
			.addIngredient(addIngredient.toString())
			.removeIngredient(removeIngredient.toString())
			.memo(dto.getMemo())
			.build());

		recipe.completeCook();

		return "요리 기록이 추가되었습니다.";
	}
}
