package com.s005.fif.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.auth.jwt.Jwt;
import com.s005.fif.common.response.Response;
import com.s005.fif.dto.request.MemberLoginRequestDto;
import com.s005.fif.dto.request.MemberOnboardingRequestDto;
import com.s005.fif.dto.response.MemberDetailResponseDto;
import com.s005.fif.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	/**
	 * 사용자 아이디를 받아서 액세스 토큰 발급.
	 * 액세스 토큰은 응답의 "accessToken" 값에 할당
	 * */
	@PostMapping
	@Operation(summary = "로그인")
	public Response login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
		Jwt jwt = memberService.login(memberLoginRequestDto);
		return new Response("accessToken", jwt.accessToken());
	}

	@GetMapping
	@Operation(summary = "사용자 목록 조회")
	@ApiResponse(
		content = {
			@Content(
				mediaType = "application/json",
				array = @ArraySchema(schema = @Schema(implementation = MemberDto.class))
			)
		}
	)
	public Response getMembers() {
		return new Response("memberList", memberService.getMemeberList());
	}

	@GetMapping("/info")
	@Operation(summary = "사용자 정보 상세 조회")
	@ApiResponse(
		content = {
			@Content(
				mediaType = "application/json",
				schema = @Schema(implementation = MemberDetailResponseDto.class)
			)
		}
	)
	public Response getMemberDetail(@Parameter(hidden = true)MemberDto memberDto) {
		MemberDetailResponseDto memberDetail = memberService.getMemberDetail(memberDto.getMemberId());
		return new Response("member", memberDetail);
	}

	@PostMapping("/onboarding")
	@Operation(summary = "온보딩 데이터 저장")
	public Response postOnboarding(@Parameter(hidden = true) MemberDto memberDto,
		@RequestBody MemberOnboardingRequestDto memberOnboardingRequestDto) {
		memberService.setMemberInfo(memberDto.getMemberId(), memberOnboardingRequestDto, true);
		return new Response();
	}

	@PutMapping("/preference")
	@Operation(summary = "사용자 온보딩 정보 수정")
	public Response putMemberPreference(@Parameter(hidden = true) MemberDto memberDto,
		@RequestBody MemberOnboardingRequestDto memberOnboardingRequestDto) {
		memberService.setMemberInfo(memberDto.getMemberId(), memberOnboardingRequestDto, false);
		return new Response();
	}

}
