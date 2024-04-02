package com.s005.fif.repository;

import java.util.List;

import com.s005.fif.entity.FridgeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeIngredientRepository extends JpaRepository<FridgeIngredient, Integer> {
	List<FridgeIngredient> findAllByFridgeFridgeId(Integer fridgeId);
}
