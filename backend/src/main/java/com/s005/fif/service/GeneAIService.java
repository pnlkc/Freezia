package com.s005.fif.service;

import com.s005.fif.dto.request.GeneAIPromptRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@Slf4j
public class GeneAIService {

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
}
