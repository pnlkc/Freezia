package com.s005.fif.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeSimpleResponseDto {
	private String name;
	private Integer cookTime;
	private String imgUrl;
	private Boolean saveYn;
}
