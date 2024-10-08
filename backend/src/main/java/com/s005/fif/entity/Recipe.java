package com.s005.fif.entity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.s005.fif.common.Constant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recipeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member member;

	@Column(nullable = false)
	@Size(max = Constant.RECIPE_NAME_LENGTH)
	private String name;

	@Column(nullable = false)
	@CreatedDate
	private LocalDate createDate;

	@Column(nullable = false)
	@LastModifiedDate
	private LocalDate updateDate;

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

	public void toggleSaveYn() {
		this.saveYn = !this.saveYn;
	}

	public void completeCook() {
		this.completeYn = true;
	}

	public void setCompleteYnFalse() {
		this.completeYn = false;
	}

	public void updateName(String name) {
		this.name = name;
	}

	private List<String[]> parseIngredients(String ingredientList) {
		return Arrays.stream(ingredientList.split(",")).map((ingredient) -> ingredient.split(":")).toList();
	}
	public List<String[]> getParsedIngredientList() {
		return parseIngredients(this.ingredientList);
	}

	public List<String[]> getParsedSeasoningList() {
		return parseIngredients(this.seasoningList);
	}

	public void updateImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
