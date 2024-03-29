package com.s005.fif.common.scheduler;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.s005.fif.common.Constant;
import com.s005.fif.common.types.RecipeRecommendType;
import com.s005.fif.common.types.RecipeType;
import com.s005.fif.dto.request.GeneAICategoryListRequestDto;
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

	/**
	 * 스케줄링을 통해 레시피 생성 메서드 자동 호출
	 */
	@Scheduled(cron = "${scheduler.cron}", zone = "Asia/Seoul")
	public void generateRecipeBySchedule() {

		if (!use) {
			log.info("레시피 생성 취소: " + LocalDateTime.now());
			return;
		}

		generateRecipe();
	}

	/**
	 * 레시피 생성 메서드
	 */
	public void generateRecipe() {

		log.info("이전 레시피 삭제");
		try {
			int cnt = recipeService.deleteOldRecipes();
			log.info("삭제된 레시피 개수: {}", cnt);
		} catch (Exception e) {
			log.info("이전 레시피 삭제 실패", e);
		}

		LocalDateTime startTime = LocalDateTime.now();
		log.info("레시피 생성 시작: " + startTime);

		int cntNewRecipes = 0;

		// TODO: 모든 사용자에 대해 생성
		int[] memberIds = {1, 2};

		for (int memberId : memberIds) {
			MemberDetailResponseDto memberDetail = memberService.getMemberDetail(memberId);

			// 냉장고 속 식재료 조회
			List<FridgeIngredientResponseDto> fridgeIngredients = fridgeIngredientService.getAllGredients(
				memberDetail.getFridgeId());
			List<String> fridgeIngredientNames = fridgeIngredients.stream()
				.map(FridgeIngredientResponseDto::getName)
				.toList();
			// 기피 식재료 조회
			List<String> memberDiseaseNames = memberService.getMemberDiseases(memberId);
			// 지병 조회
			List<String> memberDislikeIngredientNames = memberService.getMemberDislikeIngredients(memberId);

			log.info("레시피 생성 시작: 추천 레시피");
			for (RecipeRecommendType recommendType : RecipeRecommendType.values()) {
				if (RecipeRecommendType.NONE.equals(recommendType))
					continue;
				GeneAIResponseRecipeDto geneAIResponseRecipeDto;
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

				List<Integer> savedRecipeIds = null;
				try {
					savedRecipeIds = recipeService.saveGeneratedRecipe(memberId, recommendType,
						geneAIResponseRecipeDto, Constant.DEFAULT_RECIPE_IMG_URL);
				} catch (Exception e) {
					log.error("레시피 저장중 에러 발생: {}", geneAIResponseRecipeDto, e);
				}

				log.info("{} 레시피 이미지 생성", recommendType.name());
				try {
					makeRecipeImages(savedRecipeIds);
				} catch (Exception e) {
					log.error("레시피 이미지 생성 및 저장중 에러 발생", e);
				}

				cntNewRecipes++;
			}

			log.info("레시피 생성 시작: 카테고리 별 레시피");

			for (RecipeType recipeType : RecipeType.values()) {
				log.info("레시피 카테고리: {}", recipeType.getType());
				List<GeneAIResponseRecipeDto> geneAIResponseRecipeDtoList;
				try {
					GeneAICategoryListRequestDto req = GeneAICategoryListRequestDto.builder()
						.ingredients(String.join(", ", fridgeIngredientNames))
						.diseases(String.join(", ", memberDiseaseNames))
						.dislikeIngredients(String.join(", ", memberDislikeIngredientNames))
						.recipeTypes(recipeType.getType())
						.build();
					geneAIResponseRecipeDtoList = geneAIService.makeRecommendationRecipes(req, recipeType.getType());
				} catch (Exception e) {
					log.error("{} 레시피 생성 중 오류", recipeType.name(), e);
					continue;
				}

				geneAIResponseRecipeDtoList.forEach((r) -> {
					List<Integer> savedRecipeIds = null;
					try {
						savedRecipeIds = recipeService.saveGeneratedRecipe(memberId, RecipeRecommendType.NONE, r,
							Constant.DEFAULT_RECIPE_IMG_URL);
					} catch (Exception e) {
						log.error("레시피 저장중 에러 발생: {}", r, e);
					}
					// 레시피 이미지 생성
					log.info("{} 레시피 이미지 생성", recipeType.getType());
					try {
						makeRecipeImages(savedRecipeIds);
					} catch (Exception e) {
						log.error("레시피 이미지 생성 및 저장중 에러 발생", e);
					}

				});

				cntNewRecipes += geneAIResponseRecipeDtoList.size();
			}
		}



		LocalDateTime endTime = LocalDateTime.now();
		log.info("레시피 생성 종료: {}", endTime);
		Duration between = Duration.between(startTime, endTime);
		log.info("레시피 생성 소요 시간: {}", between.toMillis() + "ms");
		log.info("레시피 생성 개수: {}", cntNewRecipes);
	}

	/**
	 * 존재하는 레시피를 불러와서 알맞는 이미지를 자동 생성하고 새로 저장
	 * @param recipeIds 레시피 아이디 리스트
	 */
	private void makeRecipeImages(List<Integer> recipeIds) {
		if (recipeIds == null)
			throw new NullPointerException("레시피 아이디 목록을 받지 못했습니다.");
		recipeIds.forEach((recipeId) -> {
			try {
				String imgUrl = recipeService.generateAndSaveImage(recipeId);
				recipeService.updateRecipeImage(recipeId, imgUrl);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

}
