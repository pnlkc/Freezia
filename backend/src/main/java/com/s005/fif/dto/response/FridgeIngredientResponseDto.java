package com.s005.fif.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.s005.fif.entity.FridgeIngredient;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FridgeIngredientResponseDto {

	private Integer fridgeIngredientId;
	private String name;
	private String imgUrl;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate insertionDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate expirationDate;

	public static FridgeIngredientResponseDto fromEntity(FridgeIngredient ingredient) {
		return FridgeIngredientResponseDto.builder()
			.fridgeIngredientId(ingredient.getFridgeIngredientId())
			.name(ingredient.getIngredient().getName())
			.imgUrl(ingredient.getIngredient().getImgUrl())
			.insertionDate(ingredient.getInsertionDate())
			.expirationDate(ingredient.getExpirationDate())
			.build();
	}
}
