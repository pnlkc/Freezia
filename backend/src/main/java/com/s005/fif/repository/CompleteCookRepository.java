package com.s005.fif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s005.fif.entity.CompleteCook;
import com.s005.fif.entity.Recipe;

public interface CompleteCookRepository extends JpaRepository<CompleteCook, Integer> {
	List<CompleteCook> findByRecipe(Recipe recipe);
}
