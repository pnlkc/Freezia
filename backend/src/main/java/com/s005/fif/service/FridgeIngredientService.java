package com.s005.fif.service;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.response.FridgeIngredientResponseDto;
import com.s005.fif.entity.Fridge;
import com.s005.fif.entity.FridgeIngredient;
import com.s005.fif.entity.Ingredient;
import com.s005.fif.repository.FridgeIngredientRepository;
import com.s005.fif.repository.FridgeRepository;
import com.s005.fif.repository.IngredientRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class FridgeIngredientService {

	private final FridgeIngredientRepository fridgeIngredientRepository;
	private final IngredientRepository ingredientRepository;
	private final FridgeRepository fridgeRepository;

	/**
	 * 냉장고에 있는 모든 식재료를 조회한다.
	 * @param fridgeId : 냉장고 ID
	 * @return 냉장고 식재료 목록
	 */
	public List<FridgeIngredientResponseDto> getAllGredients(Integer fridgeId) {
		List<FridgeIngredient> fridgeIngredients = fridgeIngredientRepository.findAllByFridgeFridgeId(fridgeId);
		return fridgeIngredients.stream().map(FridgeIngredientResponseDto::fromEntity).toList();
	}

	/**
	 * 냉장고에 식재료를 추가한다.
	 * @param fridgeId : 냉장고 ID
	 * @param name : 식재료명
	 */
	@Transactional
	public void addIngredients(Integer fridgeId, String name) {
		Fridge fridge = fridgeRepository.findById(fridgeId)
			.orElseThrow(() -> new CustomException(ExceptionType.FRIDGE_NOT_FOUND));
		Ingredient ingredient = ingredientRepository.findByName(name)
			.orElseThrow(() -> new CustomException(ExceptionType.INGREDIENTS_NOT_FOUND));

		FridgeIngredient fridgeIngredient = FridgeIngredient.builder()
			.fridge(fridge)
			.ingredient(ingredient)
			.expirationDate(LocalDate.now().plusDays(ingredient.getExpirationPeriod()))
			.build();
		fridgeIngredientRepository.save(fridgeIngredient);
	}
}
