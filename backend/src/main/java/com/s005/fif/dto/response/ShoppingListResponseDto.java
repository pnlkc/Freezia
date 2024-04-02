package com.s005.fif.dto.response;

import com.s005.fif.entity.ShoppingList;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShoppingListResponseDto {
	private Integer shoppingListId;
	private String name;
	private Boolean checkYn;

	public static ShoppingListResponseDto fromEntity(ShoppingList shoppingList) {
		return ShoppingListResponseDto.builder()
			.shoppingListId(shoppingList.getShoppingListId())
			.name(shoppingList.getName())
			.checkYn(shoppingList.getCheckYn())
			.build();
	}
}
