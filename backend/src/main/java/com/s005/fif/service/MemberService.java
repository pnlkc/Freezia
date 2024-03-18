package com.s005.fif.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s005.fif.dto.response.MemberSimpleResponseDto;
import com.s005.fif.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public List<MemberSimpleResponseDto> getMemeberList() {
		return memberRepository.findAll().stream()
			.map(MemberSimpleResponseDto::fromEntity).toList();
	}

}
