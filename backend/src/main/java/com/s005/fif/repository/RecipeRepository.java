package com.s005.fif.repository;

import java.util.List;

import com.s005.fif.entity.Member;
import com.s005.fif.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	List<Recipe> findAllByMember(Member member);
}
