package com.s005.fif.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.common.types.RecipeRecommendType;
import com.s005.fif.dto.request.GeneAICategoryListRequestDto;
import com.s005.fif.dto.request.GeneAIHealthRequestDto;
import com.s005.fif.dto.request.GeneAIPromptRequestDto;
import com.s005.fif.dto.request.model.GeneAIBaseRequestDto;
import com.s005.fif.dto.response.GeneAICategoryListResponseDto;
import com.s005.fif.dto.response.GeneAIResponseRecipeDto;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.dto.response.ThreadIdResponseDto;
import com.s005.fif.entity.FridgeIngredient;
import com.s005.fif.entity.Member;
import com.s005.fif.repository.FridgeIngredientRepository;
import com.s005.fif.repository.MemberRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneAIService {

    @Value("${ai-server-base-url}")
    private String sendUrl;

    private final MemberRepository memberRepository;

    private WebClient webClient;

	private final FridgeIngredientRepository fridgeIngredientRepository;

	private final RestClient restClient = RestClient.create();

	@PostConstruct
    private void init() {
        webClient = WebClient.builder().baseUrl(sendUrl).build();
    }

    @Transactional
    public Flux<String> getStreamDataFromAI(MemberDto memberDto, GeneAIPromptRequestDto geneAIPromptRequestDto) {

        // 멤버 ID에 해당하는 Thread ID 조회
        Member member = memberRepository.findById(memberDto.getMemberId())
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));

        // Thread ID 존재하지 않을 경우 Thread ID 반환 API 호출
        if(member.getThreadId() == null){
            member.updateThreadId(getNewThreadId());
        }


        // api 요청
        Flux<String> eventStream = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/prompt")
                                .queryParam("ingredients", geneAIPromptRequestDto.getIngredients())
                                .queryParam("diseases", geneAIPromptRequestDto.getDiseases())
                                .queryParam("dislikeIngredients", geneAIPromptRequestDto.getDislikeIngredients())
                                .queryParam("prompt", geneAIPromptRequestDto.getPrompt())
                                .queryParam("threadId", member.getThreadId())
                                .build())

                .retrieve()
                .bodyToFlux(String.class);

        return eventStream;
    }

    public String getNewThreadId() {

        // 동기적으로 데이터 받아오기
        ThreadIdResponseDto result = webClient.get()
                .uri("/thread")
                .retrieve()
                .bodyToMono(ThreadIdResponseDto.class)
                .block(); // 현재 스레드를 블로킹하고 결과를 기다림

        return result.getThreadId();
    }

	/**
	 * 생체 정보 기반 레시피 추천
	 * @param recipeRecommendType 추천 타입
	 * @param healthRequestDto 요청 데이터
	 * @return 생성된 레시피 데이터
	 */
	public GeneAIResponseRecipeDto makeHealthRecipes(RecipeRecommendType recipeRecommendType, GeneAIHealthRequestDto healthRequestDto) {

		UriComponents uri = UriComponentsBuilder.fromUriString(sendUrl).build();
		String path = "/api/ai/health/" + recipeRecommendType.getName();

		return restClient.get()
			.uri(uriBuilder ->
				uriBuilder
					.scheme(uri.getScheme())
					.host(uri.getHost())
					.path(path)
					.queryParam("ingredients", healthRequestDto.getIngredients())
					.queryParam("diseases", healthRequestDto.getDiseases())
					.queryParam("dislikeIngredients", healthRequestDto.getDislikeIngredients())
					.build()
			)
			.accept(MediaType.APPLICATION_JSON)
			.exchange((clientRequest, clientResponse) -> {
				try {
					GeneAIResponseRecipeDto geneAIResponseRecipeDto = clientResponse.bodyTo(GeneAIResponseRecipeDto.class);
					if (geneAIResponseRecipeDto == null || geneAIResponseRecipeDto.getRecipeList() == null)
						throw new IOException("레시피 데이터 응답 매핑 실패");
					return geneAIResponseRecipeDto;
				} catch (Exception e) {
					log.error("레시피 생성 요청 실패: code - {}", clientResponse.getStatusCode());
					throw e;
				}
			});
	}


	public List<GeneAIResponseRecipeDto> makeRecommendationRecipes(GeneAICategoryListRequestDto categoryListRequestDto, String recipeTypes) {

		UriComponents uri = UriComponentsBuilder.fromUriString(sendUrl).build();
		String listPath = "/api/ai/category/list";
		String recipePath = "/api/ai/category/recipe";

		List<GeneAIResponseRecipeDto> recipeResponseDtoList = new ArrayList<>();

		GeneAICategoryListResponseDto listResponseDto = restClient.get()
			.uri(uriBuilder ->
				uriBuilder
					.scheme(uri.getScheme())
					.host(uri.getHost())
					.path(listPath)
					.queryParam("ingredients", categoryListRequestDto.getIngredients())
					.queryParam("diseases", categoryListRequestDto.getDiseases())
					.queryParam("dislikeIngredients", categoryListRequestDto.getDislikeIngredients())
					.queryParam("recipe_types", recipeTypes)
					.build()
			)
			.accept(MediaType.APPLICATION_JSON)
			.exchange((clientRequest, clientResponse) -> {
				try {
					GeneAICategoryListResponseDto geneAICategoryListResponseDto = clientResponse.bodyTo(
						GeneAICategoryListResponseDto.class);
					if (geneAICategoryListResponseDto == null)
						throw new IOException("레시피 데이터 응답 매핑 실패");
					return geneAICategoryListResponseDto;
				} catch (Exception e) {
					log.error("레시피 생성 요청 실패: code - {}", clientResponse.getStatusCode());
					throw e;
				}
			});

		List<String> recipeList = listResponseDto.getRecipeList();

		// for (int i = 0; i < recipeList.size(); i++) {
		for (int i = 0; i < Math.min(5, recipeList.size()); i++) { // FIXME: 테스트를 위해 최대 5개만 생성
			String recipeName = recipeList.get(i);
			restClient.get()
				.uri(uriBuilder ->
					uriBuilder
						.scheme(uri.getScheme())
						.host(uri.getHost())
						.path(recipePath)
						.queryParam("ingredients", categoryListRequestDto.getIngredients())
						.queryParam("diseases", categoryListRequestDto.getDiseases())
						.queryParam("dislikeIngredients", categoryListRequestDto.getDislikeIngredients())
						.queryParam("name", recipeName)
						.build()
				)
				.accept(MediaType.APPLICATION_JSON)
				.exchange((clientRequest, clientResponse) -> {
					try {
						GeneAIResponseRecipeDto geneAIResponseRecipeDto = clientResponse.bodyTo(
							GeneAIResponseRecipeDto.class);
						if (geneAIResponseRecipeDto == null || geneAIResponseRecipeDto.getRecipeList() == null)
							throw new IOException("레시피 데이터 응답 매핑 실패");
						recipeResponseDtoList.add(geneAIResponseRecipeDto);
					} catch (Exception e) {
						log.error("레시피 생성 요청 실패: code - {}", clientResponse.getStatusCode());
						// throw e;
					}
					return "";
				});
		}

		return recipeResponseDtoList;
	}

	public GeneAIBaseRequestDto getGeneAIBaseRequest(Integer memberId) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));

		List<FridgeIngredient> ingredients = fridgeIngredientRepository.findAllByFridgeFridgeId(
			member.getFridge().getFridgeId());

		return GeneAIBaseRequestDto.builder().build();
	}

}
