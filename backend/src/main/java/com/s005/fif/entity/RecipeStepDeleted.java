package com.s005.fif.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.s005.fif.common.Constant;
import com.s005.fif.entity.converter.TypeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@EntityListeners(AuditingEntityListener.class)
public class RecipeStepDeleted {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recipeStepId;

	@Column(nullable = false)
	private Integer recipeId;

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

	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime deletedDateTime;

	public static RecipeStepDeleted fromRecipeStep(RecipeStep recipeStep, Integer recipeId) {
		return RecipeStepDeleted.builder()
			.recipeId(recipeId)
			.stepNumber(recipeStep.getStepNumber())
			.description(recipeStep.getDescription())
			.type(recipeStep.getType())
			.tip(recipeStep.getTip())
			.timer(recipeStep.getTimer())
			// .deletedDateTime(LocalDateTime.now())
			.build();
	}
}
