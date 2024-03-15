package com.s005.fif.entity;

import com.s005.fif.common.Constant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CautionIngredientRel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cautionIngredientRelId;

	@Size(max = Constant.COMMON_CONTENT_LENGTH)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_id", nullable = false)
	private Ingredient ingredient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disease_id", nullable = false)
	private Disease disease;

}
