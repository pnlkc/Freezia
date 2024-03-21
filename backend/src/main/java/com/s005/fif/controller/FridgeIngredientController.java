package com.s005.fif.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import com.s005.fif.dto.response.FridgeIngredientResponseDto;
import com.s005.fif.service.FridgeIngredientService;

@RestController
@RequestMapping("/api/fridge-ingredients")
@RequiredArgsConstructor
public class FridgeIngredientController {

	private final FridgeIngredientService fridgeIngredientService;

	@GetMapping
	@Operation(summary = "전체 식재료 재고 조회")
	@ApiResponse(
		content = {
			@Content(
				mediaType = "application/json",
				array = @ArraySchema(schema = @Schema(implementation = FridgeIngredientResponseDto.class))
			)
		}
	)
	public Response getAllGradients(@Parameter(hidden = true) MemberDto memberDto) {
		return new Response("fridgeIngredients", fridgeIngredientService.getAllGredients(memberDto.getFridgeId()));
	}

	@PostMapping
	@Operation(summary = "식재료 입고")
	public Response addIngredients(@Parameter(hidden = true) MemberDto memberDto, @RequestBody FridgeIngredientInputRequestDto dto) {
		fridgeIngredientService.addIngredients(memberDto.getMemberId(), dto.getName());
		return new Response(Response.MESSAGE, "입고되었습니다.");
	}

	@DeleteMapping
	@Operation(summary = "식재료 출고")
	public Response removeIngredients(@Parameter(hidden = true) MemberDto memberDto, @RequestBody FridgeIngredientOutputRequestDto dto) {
		fridgeIngredientService.removeIngredients(memberDto.getMemberId(), dto.getFridgeIngredientId());
		return new Response(Response.MESSAGE, "출고되었습니다.");
	}
}
