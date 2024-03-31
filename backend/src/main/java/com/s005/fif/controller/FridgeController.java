package com.s005.fif.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.response.Response;
import com.s005.fif.dto.request.FridgeTokenRequestDto;
import com.s005.fif.service.FridgeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fridge")
public class FridgeController {

	private final FridgeService fridgeService;

	@PostMapping("/{fridgeId}")
	public Response registerFridgeToken(@PathVariable Integer fridgeId,
										@RequestBody FridgeTokenRequestDto requestDto) {
		fridgeService.registerFridgeToken(fridgeId, requestDto.getFridgeToken());
		return new Response(Response.MESSAGE, "토큰이 등록되었습니다.");
	}
}
