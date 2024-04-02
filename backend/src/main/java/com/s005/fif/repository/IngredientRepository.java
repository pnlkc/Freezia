package com.s005.fif.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s005.fif.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
	Optional<Ingredient> findByName(String name);
}
