package com.s005.fif.controller;

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
}
