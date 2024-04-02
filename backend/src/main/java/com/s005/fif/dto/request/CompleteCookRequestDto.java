package com.s005.fif.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.s005.fif.common.Constant;
import com.s005.fif.dto.request.model.IngredientNameDto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CompleteCookRequestDto {
	private List<IngredientNameDto> addIngredients = new ArrayList<>();
	private List<IngredientNameDto> removeIngredients = new ArrayList<>();
	@Size(max = Constant.COMMON_LARGE_CONTENT_LENGTH)
	private String memo;
}
