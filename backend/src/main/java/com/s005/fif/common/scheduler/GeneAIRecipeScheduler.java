package com.s005.fif.common.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.s005.fif.common.types.RecipeRecommendType;
import com.s005.fif.dto.request.GeneAIHealthRequestDto;
import com.s005.fif.dto.response.FridgeIngredientResponseDto;
import com.s005.fif.dto.response.GeneAIResponseRecipeDto;
import com.s005.fif.dto.response.MemberDetailResponseDto;
import com.s005.fif.service.FridgeIngredientService;
import com.s005.fif.service.GeneAIService;
import com.s005.fif.service.MemberService;
import com.s005.fif.service.RecipeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeneAIRecipeScheduler {

	private final FridgeIngredientService fridgeIngredientService;
	private final MemberService memberService;
	private final GeneAIService geneAIService;
	private final RecipeService recipeService;

	@Value("${scheduler.use}")
	private boolean use;

	@Value("${scheduler.cron}")
	private String cron;

	// @Scheduled(cron = cron)
	@Scheduled(fixedDelay = 24 * 60 * 1000) // 테스트용
	public void generateRecipe() {

		if (!use) {
			log.info("레시피 생성 취소: " + LocalDateTime.now());
			return;
		}

		LocalDateTime startTime = LocalDateTime.now();
		log.info("레시피 생성 시작: " + startTime);

		int cntNewRecipes = 0;

		// TODO: 모든 사용자에 대해 생성
		int tmpMemberId = 1;

		MemberDetailResponseDto memberDetail = memberService.getMemberDetail(tmpMemberId);

		// 냉장고 속 식재료 조회
		List<FridgeIngredientResponseDto> fridgeIngredients = fridgeIngredientService.getAllGredients(
			memberDetail.getFridgeId());
		List<String> fridgeIngredientNames = fridgeIngredients.stream().map(FridgeIngredientResponseDto::getName).toList();
		// 기피 식재료 조회
		List<String> memberDiseaseNames = memberService.getMemberDiseases(tmpMemberId);
		// 지병 조회
		List<String> memberDislikeIngredientNames = memberService.getMemberDislikeIngredients(tmpMemberId);

		log.info("레시피 생성 시작: 생체 정보 기반");
		for (RecipeRecommendType recommendType : RecipeRecommendType.values()) {
			GeneAIResponseRecipeDto geneAIResponseRecipeDto;
			String imgUrl = "example-image-url"; // TODO: 생성형 이미지로 변경
			try {
				GeneAIHealthRequestDto req = GeneAIHealthRequestDto.builder()
					.ingredients(String.join(", ", fridgeIngredientNames))
					.diseases(String.join(", ", memberDiseaseNames))
					.dislikeIngredients(String.join(", ", memberDislikeIngredientNames))
					.build();
				geneAIResponseRecipeDto = geneAIService.makeHealthRecipes(recommendType, req);

			} catch (Exception e) {
				log.error("{} 레시피 생성 중 오류", recommendType.name(), e);
				continue;
			}

			try {
				recipeService.saveGeneratedRecipe(tmpMemberId, recommendType, geneAIResponseRecipeDto, imgUrl);
			} catch (Exception e) {
				log.error("레시피 저장중 에러 발생: {}", geneAIResponseRecipeDto, e);
			}

			cntNewRecipes++;
		}

		log.info("레시피 생성 시작: 카테고리 별 레시피 생성");
		// TODO: 카테고리 별 레시피 생성

		// TODO: 카테고리 별 레시피 저장

		// TODO: 이전 레시피 삭제

		LocalDateTime endTime = LocalDateTime.now();
		log.info("레시피 생성 종료: {}" ,endTime);
		Duration between = Duration.between(startTime, endTime);
		log.info("레시피 생성 소요 시간: {}", between.toMillis() + "ms");
		log.info("레시피 생성 개수: {}", cntNewRecipes);
	}

}
