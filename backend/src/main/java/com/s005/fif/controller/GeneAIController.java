package com.s005.fif.controller;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.response.Response;
import com.s005.fif.dto.request.GeneAIPromptRequestDto;
import com.s005.fif.service.GeneAIService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/generate-AI")
@RequiredArgsConstructor
public class GeneAIController {

    private final GeneAIService geneAIService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "대화형 레시피 추천")
    public Flux<String> streamDataFromAI(@Parameter(hidden = true) MemberDto memberDto, GeneAIPromptRequestDto geneAIPromptRequestDto) {

        // FastAPI에서 SSE 스트림을 받는 로직
        Flux<String> fastApiStream = geneAIService.getStreamDataFromAI(memberDto, geneAIPromptRequestDto);

        // 받아온 스트림을 그대로 클라이언트에 전송
        return fastApiStream;
    }

}
