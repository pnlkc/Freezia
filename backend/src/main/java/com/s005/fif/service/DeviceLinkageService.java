package com.s005.fif.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.fcm.DeviceType;
import com.s005.fif.dto.fcm.FcmRecipeDto;
import com.s005.fif.dto.fcm.FcmRecipeSenderDto;
import com.s005.fif.dto.fcm.FcmSendDto;
import com.s005.fif.dto.fcm.FcmStepShiftingDto;
import com.s005.fif.dto.fcm.FcmTokenDto;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.dto.response.RecipeStepResponseDto;
import com.s005.fif.entity.Member;
import com.s005.fif.repository.MemberRepository;

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
		// 레시피 정보 조회
		RecipeResponseDto recipe = recipeService.getRecipe(memberId, recipeId);
		List<RecipeStepResponseDto> recipSteps = recipeService.getRecipeSteps(memberId, recipeId);

		// 메시지 생성
		FcmRecipeDto fcmRecipeDto = FcmRecipeDto.builder()
			.type(2)
			.recipeInfo(recipe)
			.recipeSteps(recipSteps)
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

	public void disconnectWithGalaxyWatch(Integer memberId, Integer recipeId) {
		// 웹, 워치 토큰 조회
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));
		String watchToken = member.getWatchToken();
		String webToken = member.getFridge().getFridgeToken();

		// 메시지 설정
		FcmRecipeDto fcmRecipeDto = FcmRecipeDto.builder()
			.type(3)
			.build();
		FcmSendDto fcmSendDto = FcmSendDto.builder()
			.title("삼성 비스포크 패밀리허브 냉장고 연동 종료")
			.body("냉장고 레시피와 워치 연동을 종료합니다.")
			.data(fcmRecipeDto)
			.build();

		// 워치로 전송
		fcmSendDto.setToken(watchToken);
		firebaseCloudMessageService.sendMessageTo(fcmSendDto);

		// 웹으로 전송
		fcmSendDto.setToken(webToken);
		firebaseCloudMessageService.sendMessageTo(fcmSendDto);
	}

	public void disconnectWithGalaxyWatch(Integer memberId, Integer recipeId, Integer senderId) {
		// 웹, 워치 토큰 조회
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));
		String watchToken = member.getWatchToken();
		String webToken = member.getFridge().getFridgeToken();

		// 메시지 설정
		FcmRecipeSenderDto fcmRecipeDto = FcmRecipeSenderDto.builder()
			.type(3)
			.sender(senderId)
			.build();
		FcmSendDto fcmSendDto = FcmSendDto.builder()
			.title("삼성 비스포크 패밀리허브 냉장고 연동 종료")
			.body("냉장고 레시피와 워치 연동을 종료합니다.")
			.data(fcmRecipeDto)
			.build();

		// 워치로 전송
		fcmSendDto.setToken(watchToken);
		firebaseCloudMessageService.sendMessageTo(fcmSendDto);

		// 웹으로 전송
		fcmSendDto.setToken(webToken);
		firebaseCloudMessageService.sendMessageTo(fcmSendDto);
	}

	public void moveToNextStep(Integer memberId, Integer step, Integer sender) {
		// 웹, 워치 토큰 조회
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));
		String watchToken = member.getWatchToken();
		String webToken = member.getFridge().getFridgeToken();

		FcmStepShiftingDto fcmStepShiftingDto = FcmStepShiftingDto.builder()
			.type(4)
			.step(step)
			.sender(sender)
			.build();
		FcmSendDto fcmSendDto = FcmSendDto.builder()
			.title("레시피 단계 이동")
			.body("냉장고와 워치의 레시피를 "+step+"단계로 이동합니다.")
			.data(fcmStepShiftingDto)
			.build();

		// 워치로 전송
		fcmSendDto.setToken(watchToken);
		firebaseCloudMessageService.sendMessageTo(fcmSendDto);

		// 웹으로 전송
		fcmSendDto.setToken(webToken);
		firebaseCloudMessageService.sendMessageTo(fcmSendDto);
	}

	@Transactional
	public void saveToken(Integer memberId, FcmTokenDto fcmTokenDto) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));

		if (fcmTokenDto.getType().equals(DeviceType.MOBILE)) {
			member.updateMobileToken(fcmTokenDto.getToken());
		} else if (fcmTokenDto.getType().equals(DeviceType.WATCH)) {
			member.updateWatchToken(fcmTokenDto.getToken());
		}

		memberRepository.save(member);
	}
}
