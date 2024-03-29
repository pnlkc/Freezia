package com.s005.fif.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.response.Response;
import com.s005.fif.dto.fcm.FCMStepShiftingRequestDto;
import com.s005.fif.dto.fcm.FcmTokenDto;
import com.s005.fif.service.DeviceLinkageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeviceLinkageController {

	private final DeviceLinkageService deviceLinkageService;

	@PostMapping("/recipes/{recipeId}/galaxy-watch")
	@Operation(summary = "갤럭시 워치 연동")
	public Response connectWithGalaxyWatch(@Parameter(hidden = true) MemberDto memberDto, @PathVariable Integer recipeId) {
		deviceLinkageService.connectWithGalaxyWatch(memberDto.getMemberId(), recipeId);
		return new Response(Response.MESSAGE, "연동되었습니다.");
	}

	@GetMapping("/recipes/{recipeId}/galaxy-watch")
	@Operation(summary = "갤럭시 워치 연동 종료")
	public Response disconnectWithGalaxyWatch(@Parameter(hidden = true) MemberDto memberDto, @PathVariable Integer recipeId) {
		deviceLinkageService.disconnectWithGalaxyWatch(memberDto.getMemberId(), recipeId);
		return new Response(Response.MESSAGE, "연동이 종료되었습니다.");
	}

	@PostMapping("/recipes/steps/{step}")
	@Operation(summary = "워치&패널 레시피 단계 이동")
	public Response moveToNextStep(@Parameter(hidden = true) MemberDto memberDto, @PathVariable Integer step, @RequestBody FCMStepShiftingRequestDto requestDto) {
		deviceLinkageService.moveToNextStep(memberDto.getMemberId(), step, requestDto.getSender());
		return new Response(Response.MESSAGE,  step +" 단계로 이동합니다.");
	}

	@PostMapping("/fcm/token")
	@Operation(summary = "디바이스 토큰 갱신")
	public Response save(@Parameter(hidden = true) MemberDto memberDto, @RequestBody FcmTokenDto fcmTokenDto) {
		deviceLinkageService.saveToken(memberDto.getMemberId(), fcmTokenDto);
		return new Response(Response.MESSAGE, "새로운 토큰이 등록 되었습니다.");
	}

}
