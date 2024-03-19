package com.s005.fif.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.s005.fif.dto.response.model.IngredientDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompleteCookResponseDto {
	private List<IngredientDto> addIngredients;
	private List<IngredientDto> removeIngredients;
	private String memo;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate completeDate;
}
