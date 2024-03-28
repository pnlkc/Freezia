package com.s005.fif.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class GeneAICategoryListResponseDto {
	private List<String> recipeList;
}
