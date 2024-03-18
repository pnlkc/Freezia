package com.s005.fif.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.response.MemberDetailResponseDto;
import com.s005.fif.dto.response.MemberSimpleResponseDto;
import com.s005.fif.entity.Member;
import com.s005.fif.repository.DislikeIngredientRepository;
import com.s005.fif.repository.MemberDiseaseRepository;
import com.s005.fif.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberDiseaseRepository memberDiseaseRepository;
	private final DislikeIngredientRepository dislikeIngredientRepository;

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
