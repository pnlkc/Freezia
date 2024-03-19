package com.s005.fif.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShoppingListRequestDto {
	private Integer shoppingListId;
	private String name;
	private Boolean checkYn;
}