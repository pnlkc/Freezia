package com.s005.fif.dto.fcm;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.s005.fif.dto.response.model.IngredientDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FcmRecipeDto {
	private Integer type;
	private Integer recipeId;
	private String name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate createDate;
	private Integer cookTime;
	private Integer calorie;
	private List<IngredientDto> ingredientList;
	private List<IngredientDto> seasoningList;
	private String imgUrl;
	private Boolean saveYn;
	private Boolean completeYn;
	private Integer recommendType;
	private String recipeTypes;
	private Integer serving;
}
