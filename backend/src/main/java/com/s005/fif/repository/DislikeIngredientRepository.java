package com.s005.fif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s005.fif.entity.DislikeIngredient;
import com.s005.fif.entity.Member;

public interface DislikeIngredientRepository extends JpaRepository<DislikeIngredient, Integer> {
	List<DislikeIngredient> findAllByMember(Member member);
}
