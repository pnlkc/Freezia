package com.s005.fif.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.s005.fif.common.Constant;
import com.s005.fif.entity.converter.TypeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

@Entity
@Getter
@Setter
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
	private Integer stepNumber;

	@Column(nullable = false)
	@Size(max = Constant.COMMON_CONTENT_LENGTH)
	private String description;

	@Convert(converter = TypeConverter.class)
	@Column(nullable = false)
	private Type type;

	@Size(max = Constant.COMMON_CONTENT_LENGTH)
	private String tip;

	private Integer timer;
}
