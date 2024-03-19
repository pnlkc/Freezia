package com.s005.fif.service;

import org.springframework.stereotype.Service;

import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.FcmRecipeDto;
import com.s005.fif.dto.FcmSendDto;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.entity.Member;
import com.s005.fif.repository.MemberRepository;
import com.s005.fif.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceLinkageService {

	private final MemberRepository memberRepository;
	private final RecipeService recipeService;
	private final FirebaseCloudMessageService firebaseCloudMessageService;

	public void connectWithGalaxyWatch(Integer memberId, Integer recipeId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));
		String watchToken = member.getWatchToken();
		if(watchToken.isEmpty() || watchToken.isBlank()) {
			throw new CustomException(ExceptionType.DEVICE_CONNECTION_FAILED);
		}
		// TODO 레시피 정보 조회
		FcmRecipeDto fcmRecipeDto = FcmRecipeDto.builder()
			.type(2)
			.build();

		// 워치로 연동 요청 전송
		FcmSendDto fcmSendDto = FcmSendDto.builder()
			.token(watchToken)
			.title("삼성 비스포크 패밀리허브 냉장고 연동")
			.body("냉장고 레시피를 연동하시겠어요?")
			.data(fcmRecipeDto)
			.build();
		firebaseCloudMessageService.sendMessageTo(fcmSendDto);
	}
}
