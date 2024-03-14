package com.s005.fif.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.dto.response.model.IngredientDto;
import com.s005.fif.entity.Ingredient;
import com.s005.fif.entity.Member;
import com.s005.fif.entity.Recipe;
import com.s005.fif.repository.IngredientRepository;
import com.s005.fif.repository.MemberRepository;
import com.s005.fif.repository.RecipeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {

	private final MemberRepository memberRepository;
	private final RecipeRepository recipeRepository;
	private final IngredientRepository ingredientRepository;

	public Member getMemberIdFromToken(String token) {
		Integer memberId = 1;	// TODO : 로직 변경하기
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));
	}

	/**
	 * 레시피 세부 정보를 반환
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
		String[] ingredientNames = recipe.getIngredientList().split(",");
		for (String ingredientName : ingredientNames) {
			Optional<Ingredient> findIngredient = ingredientRepository.findByName(ingredientName);
			// 식재료 DB에 없는 경우
			if (findIngredient.isEmpty()) {

			}
			// 식재료 DB에 있는 경우
			else {
				Ingredient ingredient = findIngredient.get();
				IngredientDto ingredientDto = IngredientDto.builder()
					.name(ingredient.getName())
					.image(ingredient.getImgUrl())
					.amounts(null)	// TODO : recipe 테이블에 필요 식재료 양 담는 방법?
					.unit(ingredient.getUnit())
					.build();

				// 식재료인 경우
				if (!ingredient.getSeasoningYn()) {
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
}
