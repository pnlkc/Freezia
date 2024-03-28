package com.s005.fif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.s005.fif.entity.Member;
import com.s005.fif.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	List<Recipe> findAllByMember(Member member);
	List<Recipe> findAllByMemberAndSaveYn(Member member, boolean saveYn);
	List<Recipe> findAllByMemberAndCompleteYn(Member member, boolean completeYn);
	List<Recipe> findByMemberAndRecommendTypeOrderByCreateDateDesc(Member member, Integer recommendType);
	List<Recipe> findAllByCompleteYnAndSaveYn(Boolean completeYn, Boolean saveYn);
	void deleteAllByCompleteYnAndSaveYn(Boolean completeYn, Boolean saveYn);
}
