package com.s005.fif.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.response.Response;
import com.s005.fif.dto.response.ShoppingListResponseDto;
import com.s005.fif.service.ShoppingListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shopping-list")
public class ShoppingListController {

	private final ShoppingListService shoppingListService;

	@GetMapping
	public Response getShoppingList(MemberDto memberDto) {
		List<ShoppingListResponseDto> shoppingList = shoppingListService.getShoppingList(memberDto.getMemberId());
		return new Response("shoppingList", shoppingList);
	}
}
