package com.s005.fif.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.response.Response;
import com.s005.fif.service.DeviceLinkageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeviceLinkageController {

	private final DeviceLinkageService deviceLinkageService;

	@PostMapping("/recipes/{recipeId}/galaxy-watch")
	public Response connectWithGalaxyWatch(@PathVariable Integer recipeId) {
		Integer memberId = 1;
		deviceLinkageService.connectWithGalaxyWatch(memberId, recipeId);
		return new Response(Response.MESSAGE, "연동되었습니다.");
	}

	@GetMapping("/recipes/{recipeId}/galaxy-watch")
	public Response disconnectWithGalaxyWatch(@PathVariable Integer recipeId) {
		Integer memberId = 1;
		deviceLinkageService.disconnectWithGalaxyWatch(memberId, recipeId);
		return new Response(Response.MESSAGE, "연동이 종료되었습니다.");
	}

	@GetMapping("/api/recipes/step/{step}")
	public Response moveToNextStep(@PathVariable Integer step) {
		Integer memberId = 1;
		deviceLinkageService.moveToNextStep(memberId, step);
		return new Response(Response.MESSAGE,  step +" 단계로 이동합니다.");
	}
}
