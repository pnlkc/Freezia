package com.s005.fif.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.response.Response;
import com.s005.fif.service.DeviceLinkageService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeviceLinkageController {

	private final DeviceLinkageService deviceLinkageService;

	@PostMapping("/recipes/{recipeId}/galaxy-watch")
	@Operation(summary = "갤럭시 워치 연동")
	public Response connectWithGalaxyWatch(MemberDto memberDto, @PathVariable Integer recipeId) {
		deviceLinkageService.connectWithGalaxyWatch(memberDto.getMemberId(), recipeId);
		return new Response(Response.MESSAGE, "연동되었습니다.");
	}

	@GetMapping("/recipes/{recipeId}/galaxy-watch")
	@Operation(summary = "갤럭시 워치 연동 종료")
	public Response disconnectWithGalaxyWatch(MemberDto memberDto, @PathVariable Integer recipeId) {
		deviceLinkageService.disconnectWithGalaxyWatch(memberDto.getMemberId(), recipeId);
		return new Response(Response.MESSAGE, "연동이 종료되었습니다.");
	}

	@GetMapping("/api/recipes/step/{step}")
	@Operation(summary = "워치&패널 레시피 단계 이동")
	public Response moveToNextStep(MemberDto memberDto, @PathVariable Integer step) {
		deviceLinkageService.moveToNextStep(memberDto.getMemberId(), step);
		return new Response(Response.MESSAGE,  step +" 단계로 이동합니다.");
	}
}
