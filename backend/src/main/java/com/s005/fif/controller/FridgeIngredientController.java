package com.s005.fif.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.response.Response;
import com.s005.fif.dto.request.FridgeIngredientRequestDto;
import com.s005.fif.dto.response.FridgeIngredientResponseDto;
import com.s005.fif.service.FridgeIngredientService;

@RestController
@RequestMapping("/api/fridge-ingredients")
@RequiredArgsConstructor
public class FridgeIngredientController {

	private final FridgeIngredientService fridgeIngredientService;

	@GetMapping
	public Response getAllGradients() {
		Integer fridgeId = 1;
		return new Response("fridgeIngredients", fridgeIngredientService.getAllGredients(fridgeId));
	}

	@PostMapping
	public Response addIngredients(@RequestBody FridgeIngredientRequestDto dto) {
		Integer fridgeId = 1;
		fridgeIngredientService.addIngredients(fridgeId, dto.getName());
		return new Response(Response.MESSAGE, "입고되었습니다.");
	}
}
