package com.s005.fif.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.auth.jwt.Jwt;
import com.s005.fif.common.auth.jwt.JwtTokenProvider;
import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.request.MemberLoginRequestDto;
import com.s005.fif.dto.response.MemberDetailResponseDto;
import com.s005.fif.dto.response.MemberSimpleResponseDto;
import com.s005.fif.entity.Member;
import com.s005.fif.repository.DislikeIngredientRepository;
import com.s005.fif.repository.MemberDiseaseRepository;
import com.s005.fif.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberDiseaseRepository memberDiseaseRepository;
	private final DislikeIngredientRepository dislikeIngredientRepository;

	/**
	 * 사용자 확인 후 토큰 생성
	 * */
	public Jwt login(MemberLoginRequestDto memberLoginRequestDto) {

		Integer memberId = memberLoginRequestDto.getMemberId();
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

		return jwtTokenProvider.createJwt(MemberDto.builder()
			.memberId(member.getMemberId())
			.fridgeId(member.getFridge().getFridgeId())
			.build());
	}

	/**
	 * 유효한 사용자인지 확인
	 * */
	public boolean verifyMember(MemberLoginRequestDto memberLoginRequestDto) {
		return memberRepository.findById(memberLoginRequestDto.getMemberId()).isPresent();
	}

	public List<MemberSimpleResponseDto> getMemeberList() {
		return memberRepository.findAll().stream()
			.map(MemberSimpleResponseDto::fromEntity).toList();
	}

	public MemberDetailResponseDto getMemberDetail(Integer memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));
		List<Integer> diseaseIds = memberDiseaseRepository.findAllByMember(member)
			.stream()
			.map((md) -> md.getDisease().getDiseaseId())
			.toList();
		List<Integer> dislikeIngredientIds = dislikeIngredientRepository.findAllByMember(member)
			.stream()
			.map((di) -> di.getIngredient().getIngredientId())
			.toList();

		return MemberDetailResponseDto.fromEntity(member, diseaseIds, dislikeIngredientIds);
	}

}
