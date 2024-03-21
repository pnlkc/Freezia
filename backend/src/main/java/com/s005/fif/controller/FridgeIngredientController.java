package com.s005.fif.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.response.Response;
import com.s005.fif.dto.request.FridgeIngredientInputRequestDto;
import com.s005.fif.dto.request.FridgeIngredientOutputRequestDto;
import com.s005.fif.service.FridgeIngredientService;

@RestController
@RequestMapping("/api/fridge-ingredients")
@RequiredArgsConstructor
public class FridgeIngredientController {

	private final FridgeIngredientService fridgeIngredientService;

	@GetMapping
	public Response getAllGradients(MemberDto memberDto) {
		return new Response("fridgeIngredients", fridgeIngredientService.getAllGredients(memberDto.getFridgeId()));
	}

	@PostMapping
	public Response addIngredients(MemberDto memberDto, @RequestBody FridgeIngredientInputRequestDto dto) {
		fridgeIngredientService.addIngredients(memberDto.getMemberId(), dto.getName());
		return new Response(Response.MESSAGE, "입고되었습니다.");
	}

	@DeleteMapping
	public Response removeIngredients(MemberDto memberDto, @RequestBody FridgeIngredientOutputRequestDto dto) {
		fridgeIngredientService.removeIngredients(memberDto.getMemberId(), dto.getFridgeIngredientId());
		return new Response(Response.MESSAGE, "출고되었습니다.");
	}
}
