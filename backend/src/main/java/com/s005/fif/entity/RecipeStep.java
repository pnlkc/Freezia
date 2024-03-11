package com.s005.fif.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.s005.fif.common.Constant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
public class RecipeStep {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recipeStepId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Recipe recipe;

	@Column(nullable = false)
	private Integer order;

	@Column(nullable = false)
	@Size(max = Constant.COMMON_CONTENT_LENGTH)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Type type;

	@Size(max = Constant.COMMON_CONTENT_LENGTH)
	private String tip;

	private Integer timer;
}
