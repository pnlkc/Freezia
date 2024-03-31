package com.s005.fif.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.s005.fif.common.Constant;

import jakarta.persistence.Column;
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
public class RecipeDeleted {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recipeId;

	@Column(nullable = false)
	private Integer memberId;

	@Column(nullable = false)
	@Size(max = Constant.RECIPE_NAME_LENGTH)
	private String name;

	@Column(nullable = false)
	// @CreatedDate
	private LocalDate createDate;

	@Column(nullable = false)
	// @LastModifiedDate
	private LocalDate updateDate;

	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime deletedDateTime;

	@Column(nullable = false)
	private Integer cookTime;

	@Column(nullable = false)
	private Integer calorie;

	@Column(nullable = false)
	@Size(max = Constant.INGREDIENT_LIST_LENGTH)
	private String ingredientList;

	@Column(nullable = false)
	@Size(max = Constant.INGREDIENT_LIST_LENGTH)
	private String seasoningList;

	@Column(nullable = false)
	@Size(max = Constant.COMMON_URL_LENGTH)
	private String imgUrl;

	@Column(nullable = false)
	private Boolean saveYn;

	@Column(nullable = false)
	private Boolean completeYn;

	@Column(nullable = false)
	private Integer recommendType;

	@Size(max = Constant.COMMON_LARGE_CONTENT_LENGTH)
	private String recommendDesc;

	@Column(nullable = false)
	@Size(max = Constant.RECIPE_TYPE_LIST_LENGTH)
	private String recipeTypes;

	@Column(nullable = false)
	@ColumnDefault("1")
	private Integer serving;

	public static RecipeDeleted fromRecipe(Recipe recipe, Integer memberId) {
		return RecipeDeleted.builder()
			.memberId(memberId)
			.name(recipe.getName())
			.createDate(recipe.getCreateDate())
			.updateDate(recipe.getUpdateDate())
			// .deletedDateTime(LocalDateTime.now())
			.cookTime(recipe.getCookTime())
			.calorie(recipe.getCalorie())
			.ingredientList(recipe.getIngredientList())
			.seasoningList(recipe.getSeasoningList())
			.imgUrl(recipe.getImgUrl())
			.saveYn(recipe.getSaveYn())
			.completeYn(recipe.getCompleteYn())
			.recommendType(recipe.getRecommendType())
			.recommendDesc(recipe.getRecommendDesc())
			.recipeTypes(recipe.getRecipeTypes())
			.serving(recipe.getServing())
			.build();
	}

}
