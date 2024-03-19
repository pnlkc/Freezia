package com.s005.fif.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.response.ShoppingListResponseDto;
import com.s005.fif.entity.Member;
import com.s005.fif.repository.MemberRepository;
import com.s005.fif.repository.ShoppingListRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingListService {

	private final MemberRepository memberRepository;
	private final ShoppingListRepository shoppingListRepository;

	public List<ShoppingListResponseDto> getShoppingList(Integer memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));

		return shoppingListRepository.findAllByMember(member)
			.stream()
			.map(ShoppingListResponseDto::fromEntity)
			.toList();
	}

}
