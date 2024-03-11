package com.s005.fif.entity;

import java.time.LocalDate;

import com.s005.fif.common.Constant;

import jakarta.persistence.Column;
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
public class CompleteCook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer completeCookId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", nullable = false)
	private Recipe recipe;

	@Column(nullable = false)
	private LocalDate completeDate;

	@Size(max = Constant.INGREDIENT_LIST_LENGTH)
	private String addIngredient;

	@Size(max = Constant.INGREDIENT_LIST_LENGTH)
	private String removeIngredient;

	@Size(max = Constant.COMMON_LARGE_CONTENT_LENGTH)
	private String memo;
}
