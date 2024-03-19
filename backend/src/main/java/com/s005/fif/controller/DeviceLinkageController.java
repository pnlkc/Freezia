package com.s005.fif.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.response.Response;
import com.s005.fif.service.DeviceLinkageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeviceLinkageController {

	private final DeviceLinkageService deviceLinkageService;

	@PostMapping("/recipes/{recipeId}/galaxy-watch")
	public Response connectWithGalaxyWatch(MemberDto memberDto, @PathVariable Integer recipeId) {
		deviceLinkageService.connectWithGalaxyWatch(memberDto.getMemberId(), recipeId);
		return new Response(Response.MESSAGE, "연동되었습니다.");
	}

	@GetMapping("/recipes/{recipeId}/galaxy-watch")
	public Response disconnectWithGalaxyWatch(MemberDto memberDto, @PathVariable Integer recipeId) {
		deviceLinkageService.disconnectWithGalaxyWatch(memberDto.getMemberId(), recipeId);
		return new Response(Response.MESSAGE, "연동이 종료되었습니다.");
	}

	@GetMapping("/api/recipes/step/{step}")
	public Response moveToNextStep(MemberDto memberDto, @PathVariable Integer step) {
		deviceLinkageService.moveToNextStep(memberDto.getMemberId(), step);
		return new Response(Response.MESSAGE,  step +" 단계로 이동합니다.");
	}
}
