package com.s005.fif.dto.request.model;

import com.s005.fif.common.Constant;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AddIngredientDto {
	@Size(max = Constant.COMMON_TITLE_LENGTH)
	private String name;
	private Integer amounts;
	private String unit;
}
