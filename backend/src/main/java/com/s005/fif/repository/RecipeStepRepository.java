package com.s005.fif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s005.fif.entity.Recipe;
import com.s005.fif.entity.RecipeStep;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Integer> {
	List<RecipeStep> findByRecipeOrderByStepNumberAsc(Recipe recipe);
	List<RecipeStep> findByRecipe(Recipe recipe);
}
