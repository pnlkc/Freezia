package com.s005.fif.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.auth.jwt.Jwt;
import com.s005.fif.common.auth.jwt.JwtTokenProvider;
import com.s005.fif.common.response.Response;
import com.s005.fif.dto.request.MemberLoginRequestDto;
import com.s005.fif.dto.request.MemberOnboardingRequestDto;
import com.s005.fif.dto.response.MemberDetailResponseDto;
import com.s005.fif.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberService memberService;

	/**
	 * 사용자 아이디를 받아서 액세스 토큰 발급.
	 * 액세스 토큰은 "Authorization" 헤더로 전송
	 * */
	@PostMapping
	public ResponseEntity<Response> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
		Jwt jwt = memberService.login(memberLoginRequestDto);

		HttpHeaders headers = new HttpHeaders();
		String authorizationHeader = jwtTokenProvider.toAuthorizationHeader(jwt.accessToken());
		headers.add(HttpHeaders.AUTHORIZATION, authorizationHeader);

		// // ResponseCookie 객체를 생성하고, 쿠키에 Refresh 토큰을 설정
		// ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", jwt.refreshToken())
		// 	.maxAge(jwtUtils.getREFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC())
		// 	.httpOnly(true)
		// 	// .sameSite("None") //  SameSite 속성을 제3자 쿠키에 대해 None으로 설정
		// 	.secure(true)
		// 	.path("/")
		// 	.build();
		// headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
		// xTODO: refreshToken 저장 로직

		return ResponseEntity.ok()
			.headers(headers)
			.body(new Response());
	}

	@GetMapping
	public Response getMembers() {
		return new Response("memberList", memberService.getMemeberList());
	}

	@GetMapping("/info")
	public Response getMemberDetail(MemberDto memberDto) {
		MemberDetailResponseDto memberDetail = memberService.getMemberDetail(memberDto.getMemberId());
		return new Response("member", memberDetail);
	}

	@PostMapping("/onboarding")
	public Response postOnboarding(MemberDto memberDto,
		@RequestBody MemberOnboardingRequestDto memberOnboardingRequestDto) {
		memberService.setMemberInfo(memberDto.getMemberId(), memberOnboardingRequestDto, true);
		return new Response();
	}

	@PutMapping("/preference")
	public Response putMemberPreference(MemberDto memberDto,
		@RequestBody MemberOnboardingRequestDto memberOnboardingRequestDto) {
		memberService.setMemberInfo(memberDto.getMemberId(), memberOnboardingRequestDto, false);
		return new Response();
	}

}
