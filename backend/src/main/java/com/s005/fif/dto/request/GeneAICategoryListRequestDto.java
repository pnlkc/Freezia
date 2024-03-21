package com.s005.fif.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.s005.fif.dto.request.model.GeneAIBaseRequestDto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GeneAICategoryListRequestDto extends GeneAIBaseRequestDto {
	@JsonProperty("recipe_types")
	private final String recipeTypes;
}
