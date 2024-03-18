package com.s005.fif.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.response.Response;
import com.s005.fif.dto.response.MemberDetailResponseDto;
import com.s005.fif.service.MemberService;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping
	public Response getMembers() {
		return new Response("memberList", memberService.getMemeberList());
	}

	@GetMapping("/info")
	public Response getMemberDetail(@RequestHeader(value = "Authorization", required = false) String token) {
		// TODO: 사용자 정보 조회
		Integer memberId = 1;
		MemberDetailResponseDto memberDetail = memberService.getMemberDetail(memberId);
		return new Response("member", memberDetail);
	}
}
