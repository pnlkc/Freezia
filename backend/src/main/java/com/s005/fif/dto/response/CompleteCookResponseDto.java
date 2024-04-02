package com.s005.fif.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.s005.fif.dto.response.model.IngredientSimpleDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompleteCookResponseDto {
	private Integer completeCookId;
	private List<IngredientSimpleDto> addIngredients;
	private List<IngredientSimpleDto> removeIngredients;
	private String memo;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate completeDate;
}
