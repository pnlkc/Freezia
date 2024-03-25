package com.s005.fif.service;

import com.s005.fif.dto.request.GeneAIPromptRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@Slf4j
public class GeneAIService {

    @Value("${FASTAPI_SERVER_URL}")
    private String sendUrl;

    public Flux<String> getStreamDataFromAI(GeneAIPromptRequestDto geneAIPromptRequestDto) {

        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl(sendUrl)
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
