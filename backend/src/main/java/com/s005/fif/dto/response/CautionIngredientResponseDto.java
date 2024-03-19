package com.s005.fif.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CautionIngredientResponseDto {

	private String name;
	private String description;
	private String imgUrl;
}
