package com.s005.fif.repository;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.s005.fif.entity.CautionIngredientRel;

public interface CautionIngredientRelRepository extends JpaRepository<CautionIngredientRel, Integer> {

	@Query(value = "select * from caution_ingredient_rel where ingredient_id = :ingredientId and disease_id in (\n"
		+ "\tselect disease_id\n"
		+ "\tfrom member_disease_rel\n"
		+ "\twhere member_id = :memberId\n"
		+ ")",
			nativeQuery = true)
	List<CautionIngredientRel> findAllByMemberIdAndIngredientId(Integer memberId, Integer ingredientId);
}
