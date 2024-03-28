package com.s005.fif.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.dto.request.GeneAIPromptRequestDto;
import com.s005.fif.dto.response.ThreadIdResponseDto;
import com.s005.fif.entity.Member;
import com.s005.fif.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.common.types.RecipeRecommendType;
import com.s005.fif.dto.request.GeneAICategoryListRequestDto;
import com.s005.fif.dto.request.GeneAIHealthRequestDto;
import com.s005.fif.dto.request.GeneAIPromptRequestDto;
import com.s005.fif.dto.request.model.GeneAIBaseRequestDto;
import com.s005.fif.dto.response.GeneAIResponseRecipeDto;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.entity.FridgeIngredient;
import com.s005.fif.entity.Member;
import com.s005.fif.repository.FridgeIngredientRepository;
import com.s005.fif.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

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
		String path = "/api/ai/health/" + recipeRecommendType.getName();

		return restClient.get()
			.uri(uriBuilder ->
				uriBuilder
					.scheme("https")
					.host(HOST_PATH)
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
					log.error("레시피 생성 요청 실패: \n"
						+ "Response - {}", new String(clientResponse.getBody().readAllBytes(), StandardCharsets.UTF_8));
					throw e;
				}
			});
	}


	public List<RecipeResponseDto> makeRecommendationRecipes(GeneAICategoryListRequestDto categoryListRequestDto) {
		String listUri = HOST_PATH + "/api/ai/category/list";
		String recipeUri = HOST_PATH + "/api/ai/category/recipe";

		List<RecipeResponseDto> recipeResponseDtoList = new ArrayList<>();

		return null;
	}

	public GeneAIBaseRequestDto getGeneAIBaseRequest(Integer memberId) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND));

		List<FridgeIngredient> ingredients = fridgeIngredientRepository.findAllByFridgeFridgeId(
			member.getFridge().getFridgeId());

		return GeneAIBaseRequestDto.builder().build();
	}

}
