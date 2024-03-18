package com.s005.fif.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.dto.response.RecipeStepResponseDto;
import com.s005.fif.dto.response.model.IngredientDto;
import com.s005.fif.entity.Ingredient;
import com.s005.fif.entity.Member;
import com.s005.fif.entity.Recipe;
import com.s005.fif.entity.RecipeStep;
import com.s005.fif.repository.IngredientRepository;
import com.s005.fif.repository.MemberRepository;
import com.s005.fif.repository.RecipeRepository;
import com.s005.fif.repository.RecipeStepRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {

	private final MemberRepository memberRepository;
	private final RecipeRepository recipeRepository;
	private final IngredientRepository ingredientRepository;
	private final RecipeStepRepository recipeStepRepository;

	public Member getMemberIdFromToken(String token) {
		Integer memberId = 1;	// TODO : 로직 변경하기
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));
	}

	/**
	 * 레시피 세부 정보를 반환합니다.
	 * @param token 토큰
	 * @param recipeId 레시피 ID
	 * @return 레시피 세부 정보
	 */
	public RecipeResponseDto getRecipe(String token, Integer recipeId) {
		Member member = getMemberIdFromToken(token);
		Recipe recipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new CustomException(ExceptionType.RECIPE_NOT_FOUND));

		// [예외 처리] 본인의 레시피가 아닐 경우
		if (!recipe.getMember().getMemberId().equals(member.getMemberId())) {
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
					.imgUrl("default_image.png")
					.seasoningYn(true)	// 양념으로 취급
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
	 * @param token 토큰
	 * @param recipeId 레시피 ID
	 * @return 레시피 단계로 이루어진 List
	 */
	@Transactional(readOnly = true)
	public List<RecipeStepResponseDto> getRecipeSteps(String token, Integer recipeId) {
		Member member = getMemberIdFromToken(token);
		Recipe recipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new CustomException(ExceptionType.RECIPE_NOT_FOUND));

		// [예외 처리] 본인의 레시피가 아닐 경우
		if (!recipe.getMember().getMemberId().equals(member.getMemberId())) {
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
	 * 레시피를 저장합니다.
	 * @param token 토큰
	 * @param recipeId 레시피 ID
	 * @return 저장 완료 메세지
	 */
	public String saveRecipe(String token, Integer recipeId) {
		Member member = getMemberIdFromToken(token);
		Recipe recipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new CustomException(ExceptionType.RECIPE_NOT_FOUND));

		// [예외 처리] 본인의 레시피가 아닐 경우
		if (!recipe.getMember().getMemberId().equals(member.getMemberId())) {
			throw new CustomException(ExceptionType.RECIPE_NOT_ACCESSIBLE);
		}

		recipe.saveRecipe();

		return "레시피가 저장되었습니다.";
	}
}
