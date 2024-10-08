package com.s005.fif.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.request.ShoppingListRequestDto;
import com.s005.fif.dto.response.ShoppingListResponseDto;
import com.s005.fif.entity.Member;
import com.s005.fif.entity.ShoppingList;
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

	private Member findMember(Integer memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));
	}

	private ShoppingList findShoppingList(Integer shoppingListId) {
		return shoppingListRepository.findById(shoppingListId)
			.orElseThrow(() -> new CustomException(ExceptionType.SHOPPING_LIST_NOT_FOUND));
	}

	public List<ShoppingListResponseDto> getShoppingList(Integer memberId) {
		Member member = findMember(memberId);

		return shoppingListRepository.findAllByMember(member)
			.stream()
			.map(ShoppingListResponseDto::fromEntity)
			.toList();
	}

	public void addShoppingList(Integer memberId, ShoppingListRequestDto shoppingListRequestDto) {
		Member member = findMember(memberId);

		shoppingListRepository.findAllByMemberAndName(member, shoppingListRequestDto.getName()).ifPresent((e) -> {
			throw new CustomException(ExceptionType.SHOPPING_LIST_EXIST);
		});

		ShoppingList shoppingList = ShoppingList.builder()
			.name(shoppingListRequestDto.getName())
			.member(member)
			.checkYn(false)
			.build();

		shoppingListRepository.save(shoppingList);
	}

	public Boolean checkShoppingList(Integer memberId, Integer shoppingListId) {
		Member member = findMember(memberId);
		ShoppingList shoppingList = findShoppingList(shoppingListId);

		if (!shoppingList.getMember().getMemberId().equals(member.getMemberId()))
			throw new CustomException(ExceptionType.SHOPPING_LIST_UNAUTHORIZED);

		Boolean check = shoppingList.check();
		shoppingListRepository.saveAndFlush(shoppingList);
		return check;
	}

	public void deleteShoppingList(Integer memberId, Integer shoppingListId) {
		Member member = findMember(memberId);
		ShoppingList shoppingList = findShoppingList(shoppingListId);

		if (!shoppingList.getMember().getMemberId().equals(member.getMemberId()))
			throw new CustomException(ExceptionType.SHOPPING_LIST_UNAUTHORIZED);

		shoppingListRepository.delete(shoppingList);
	}

}
