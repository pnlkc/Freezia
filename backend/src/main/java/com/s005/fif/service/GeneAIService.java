package com.s005.fif.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class GeneAIService {

    @Value("${AI_SERVER_HOST}")
    private String HOST_PATH;

    private final MemberRepository memberRepository;
    private final FridgeIngredientRepository fridgeIngredientRepository;

    private final RestClient restClient = RestClient.create();

    public void get() {
        String ingredients = "생선, 베이컨, 토마토, 계란, 대파, 김치, 마늘, 돼지고기, 목살, 삼겹살, 마라, 짜장, 배추, 무, 멸치";
        String diseases = "당뇨, 고혈압";
        String dislikeIngredients = "토마토, 오이, 가지";

        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("http://localhost:8000")
                        .build();

        // api 요청
        Map<String, Object> response =
                webClient
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/thread-assistant")
                                        .queryParam("ingredients", ingredients)
                                        .queryParam("diseases", diseases)
                                        .queryParam("dislikeIngredients", dislikeIngredients)
                                        .build())
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

        // 결과 확인
        log.info(response.toString());
        log.info(response.getClass().getName());
    }


    public void notifyOpenSocketToPython() {

        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("http://localhost:8000/api/ai")
                        .build();

        // api 요청
        Flux<ServerSentEvent> eventStream =
                webClient
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/sse-test")
                                        .build())
                        .retrieve()
                        .bodyToFlux(ServerSentEvent.class);

//                        .bodyToMono(Map.class)
//                        .block();

        eventStream.subscribe(
                content -> System.out.println("Time: " + content.event() + " Data: " + content.data()),
                error -> System.out.println("Error receiving SSE: " + error),
                () -> System.out.println("Completed!")
        );

    }

    public Flux<String> getStreamDataFromAI(GeneAIPromptRequestDto geneAIPromptRequestDto) {

        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("http://localhost:8000/api/ai")
                        .build();

        // api 요청
        Flux<String> eventStream = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/prompt")
                                .queryParam("ingredients", geneAIPromptRequestDto.getIngredients())
                                .queryParam("diseases", geneAIPromptRequestDto.getDiseases())
                                .queryParam("dislikeIngredients", geneAIPromptRequestDto.getDislikeIngredients())
                                .queryParam("prompt", geneAIPromptRequestDto.getPrompt())
                                .build())

                .retrieve()
                .bodyToFlux(String.class);

        eventStream.subscribe(
//                content -> System.out.println("Received: " + content),
//                error -> System.out.println("Error receiving SSE: " + error),
//                () -> System.out.println("Completed!")
        );
        return eventStream;
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
