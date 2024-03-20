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
import com.s005.fif.dto.request.model.IngredientNameDto;
import com.s005.fif.dto.response.CompleteCookResponseDto;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.dto.response.RecipeSimpleResponseDto;
import com.s005.fif.dto.response.RecipeStepResponseDto;
import com.s005.fif.dto.response.model.IngredientDto;
import com.s005.fif.entity.CompleteCook;
import com.s005.fif.entity.Ingredient;
import com.s005.fif.entity.Member;
import com.s005.fif.entity.Recipe;
import com.s005.fif.entity.RecipeStep;
import com.s005.fif.repository.CompleteCookRepository;
import com.s005.fif.repository.IngredientRepository;
import com.s005.fif.repository.MemberRepository;
import com.s005.fif.repository.RecipeRecommendationResponseDto;
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
	private final MemberRepository memberRepository;

	/**
	 * 레시피 목록을 반환합니다.
	 * @param memberId 사용자 ID
	 * @return 레시피 전체 목록
	 */
	public List<RecipeResponseDto> getRecipes(Integer memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));

		List<Recipe> recipes = recipeRepository.findAllByMember(member);

		return recipes.stream().map((recipe) ->
			getRecipe(memberId, recipe.getRecipeId())
		).toList();
	}

	/**
	 * 사용자가 저장한 레시피 목록을 반환합니다.
	 * @param memberId 사용자 ID
	 * @return 사용자가 저장한 레시피 전체 목록
	 */
	public List<RecipeSimpleResponseDto> getSavedRecipes(Integer memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));

		List<Recipe> recipes = recipeRepository.findAllByMemberAndSaveYn(member, true);

		return recipes.stream().map((recipe) ->
			RecipeSimpleResponseDto.builder()
				.recipeId(recipe.getRecipeId())
				.name(recipe.getName())
				.imgUrl(recipe.getImgUrl())
				.cookTime(recipe.getCookTime())
				.saveYn(recipe.getSaveYn())
				.build()
		).toList();
	}

	/**
	 * 사용자가 저장한 레시피 목록을 반환합니다.
	 * @param memberId 사용자 ID
	 * @return 사용자가 저장한 레시피 전체 목록
	 */
	public List<RecipeSimpleResponseDto> getCompletedRecipes(Integer memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));

		List<Recipe> recipes = recipeRepository.findAllByMemberAndCompleteYn(member, true);

		return recipes.stream().map((recipe) ->
			RecipeSimpleResponseDto.builder()
				.recipeId(recipe.getRecipeId())
				.name(recipe.getName())
				.imgUrl(recipe.getImgUrl())
				.cookTime(recipe.getCookTime())
				.saveYn(recipe.getSaveYn())
				.build()
		).toList();
	}

	/**
	 * 사용자가 저장한 레시피 목록을 반환합니다.
	 * @param memberId 사용자 ID
	 * @return 사용자가 저장한 레시피 전체 목록
	 */
	public List<RecipeRecommendationResponseDto> getRecommendationRecipes(Integer memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));

		ArrayList<Recipe> recipes = new ArrayList<>();

		// 1 ~ 4번에 해당하는 추천 유형만 조회
		for (int i = 1; i <= 4; i++) {
			List<Recipe> recipe =
				recipeRepository.findByMemberAndRecommendTypeOrderByCreateDateDesc(member, i);
			if (recipe.isEmpty()) {
				// TODO: 해당 추천 유형이 없다면 새로 레시피를 생성하는 요청 필요
				// gptService.makeRecommendationRecipe(member, i);
				continue;
			}
			recipes.add(recipe.get(0));
		}

		return recipes.stream().map((r) ->
			RecipeRecommendationResponseDto.fromEntity(r,
				findIngredientDTOs(r.getParsedIngredientList()),
				findIngredientDTOs(r.getParsedSeasoningList())
			)
		).toList();
	}

	private List<IngredientDto> findIngredientDTOs(List<String[]> ingredients) {
		return ingredients.stream().map((ingredient) -> {
			String name = ingredient[0];
			String amounts = ingredient[1];
			String unit = ingredient[2];

			Optional<Ingredient> findIngredientOpt = ingredientRepository.findByName(name);
			Ingredient findIngredient;

			// 식재료 DB에 없는 경우
			if (findIngredientOpt.isEmpty()) {
				/*
				추가한 식재료 중 DB에 없으면 DB에 추가
				DB에서 imgUrl, seasoningYn, expirationPeriod 직접 수정 필요함
				 */

				Ingredient newIngredient = Ingredient.builder()
					.name(name)
					.imgUrl(Constant.DEFAULT_INGREDIENT_IMG_URL)
					.seasoningYn(true)    // 양념으로 취급
					.expirationPeriod(Constant.DEFAULT_INGREDIENT_EXPIRATION_PERIOD)
					.build();

				ingredientRepository.save(newIngredient);
				findIngredient = newIngredient;
			} else {
				findIngredient = findIngredientOpt.get();
			}

			return IngredientDto.builder()
				.ingredientId(findIngredient.getIngredientId())
				.name(name)
				.image(findIngredient.getImgUrl())
				.amounts(amounts)
				.unit(unit)
				.build();
		}).toList();
	}

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
			Ingredient findIngredient;

			// 식재료 DB에 없는 경우
			if (findIngredientOpt.isEmpty()) {
				/*
				추가한 식재료 중 DB에 없으면 DB에 추가
				DB에서 imgUrl, seasoningYn, expirationPeriod 직접 수정 필요함
				 */
				ingredientRepository.save(Ingredient.builder()
					.name(name)
					.imgUrl(Constant.DEFAULT_INGREDIENT_IMG_URL)
					.seasoningYn(true)    // 양념으로 취급
					.expirationPeriod(Constant.DEFAULT_INGREDIENT_EXPIRATION_PERIOD)
					.build());

				findIngredient = ingredientRepository.findByName(name)
					.orElseThrow(() -> new CustomException(ExceptionType.INGREDIENTS_NOT_FOUND));
			}
			else {
				findIngredient = findIngredientOpt.get();
			}

			IngredientDto ingredientDto = IngredientDto.builder()
				.ingredientId(findIngredient.getIngredientId())
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

		/*
		추가한 식재료 중 DB에 없으면 DB에 추가
		DB에서 imgUrl, seasoningYn, expirationPeriod 직접 수정 필요함
		 */
		for (IngredientNameDto addIngredient : dto.getAddIngredients()) {
			if (ingredientRepository.existsByName(addIngredient.getName())) continue;
			ingredientRepository.save(Ingredient.builder()
				.name(addIngredient.getName())
				.imgUrl(Constant.DEFAULT_INGREDIENT_IMG_URL)
				.seasoningYn(true)	// 양념으로 취급
				.expirationPeriod(Constant.DEFAULT_INGREDIENT_EXPIRATION_PERIOD)
				.build());
		}

		// 추가한 식재료 stringify
		StringBuilder addIngredient = new StringBuilder();
		for (IngredientNameDto ingredient : dto.getAddIngredients()) {
			addIngredient.append(ingredient.getName()).append(",");
		}
		if (!addIngredient.isEmpty()) {
			addIngredient.deleteCharAt(addIngredient.length() - 1);
		}

		// 제외한 식재료 stringify
		StringBuilder removeIngredient = new StringBuilder();
		for (IngredientNameDto ingredient : dto.getRemoveIngredients()) {
			removeIngredient.append(ingredient.getName()).append(",");
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

	/**
	 * 요리 기록 리스트를 반환합니다.
	 * @param memberId 사용자 ID
	 * @param recipeId 레시피 ID
	 * @return 레시피에 달린 요리 기록 리스트
	 */
	@Transactional(readOnly = true)
	public List<CompleteCookResponseDto> getCompleteCook(Integer memberId, Integer recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new CustomException(ExceptionType.RECIPE_NOT_FOUND));

		// [예외 처리] 본인의 레시피가 아닐 경우
		if (!recipe.getMember().getMemberId().equals(memberId)) {
			throw new CustomException(ExceptionType.RECIPE_NOT_ACCESSIBLE);
		}

		List<CompleteCookResponseDto> completeCookResponseDtoList = new ArrayList<>();

		List<CompleteCook> completeCookList = completeCookRepository.findByRecipe(recipe);
		for (CompleteCook completeCook : completeCookList) {
			List<IngredientDto> addIngredients = new ArrayList<>();
			for (String ingredient : completeCook.getAddIngredient().split(",")) {
				String[] s = ingredient.split(":");
				String name = s[0];
				String amounts = s[1];
				String unit = s[2];

				Ingredient findIngredient = ingredientRepository.findByName(s[0])
					.orElseThrow(() -> new CustomException(ExceptionType.INGREDIENTS_NOT_FOUND));

				addIngredients.add(IngredientDto.builder()
					.ingredientId(findIngredient.getIngredientId())
					.name(name)
					.image(findIngredient.getImgUrl())
					.amounts(amounts)
					.unit(unit)
					.build());
			}

			List<IngredientDto> removeIngredients = new ArrayList<>();
			for (String ingredient : completeCook.getRemoveIngredient().split(",")) {
				Ingredient findIngredient = ingredientRepository.findByName(ingredient)
					.orElseThrow(() -> new CustomException(ExceptionType.INGREDIENTS_NOT_FOUND));

				removeIngredients.add(IngredientDto.builder()
					.ingredientId(findIngredient.getIngredientId())
					.name(ingredient)
					.image(findIngredient.getImgUrl())
					.amounts(null)
					.unit(null)
					.build());
			}

			completeCookResponseDtoList.add(CompleteCookResponseDto.builder()
				.completeCookId(completeCook.getCompleteCookId())
				.addIngredients(addIngredients)
				.removeIngredients(removeIngredients)
				.memo(completeCook.getMemo())
				.completeDate(completeCook.getCompleteDate())
				.build());
		}

		return completeCookResponseDtoList;
	}

	/**
	 * 요리 기록을 삭제합니다.
	 * @param memberId 사용자 ID
	 * @param completeCookId 요리 기록 ID
	 * @return 완료 메세지
	 */
	public String deleteCompleteCook(Integer memberId, Integer completeCookId) {
		CompleteCook completeCook = completeCookRepository.findById(completeCookId)
			.orElseThrow(() -> new CustomException(ExceptionType.COMPLETE_COOK_NOT_FOUND));

		Recipe recipe = completeCook.getRecipe();

		// [예외 처리] 본인의 레시피가 아닐 경우
		if (!recipe.getMember().getMemberId().equals(memberId)) {
			throw new CustomException(ExceptionType.RECIPE_NOT_ACCESSIBLE);
		}

		completeCookRepository.deleteById(completeCookId);

		// 레시피에 딸린 요리 기록이 없을 경우 레시피의 saveYn을 false로 업데이트
		if (!completeCookRepository.existsByRecipe(recipe)) {
			recipe.setCompleteYnFalse();
		}

		return "요리 기록이 삭제되었습니다.";
	}
}
