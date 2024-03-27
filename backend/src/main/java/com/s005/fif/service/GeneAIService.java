package com.s005.fif.service;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneAIService {

    @Value("${FASTAPI_SERVER_URL}")
    private String sendUrl;

    private final MemberRepository memberRepository;

    private WebClient webClient;

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

        eventStream.subscribe(
//                content -> System.out.println("Received: " + content),
//                error -> System.out.println("Error receiving SSE: " + error),
//                () -> System.out.println("Completed!")
        );
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
}
