package com.s005.fif.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.response.Response;
import com.s005.fif.common.scheduler.GeneAIRecipeScheduler;
import com.s005.fif.dto.request.GeneAIPromptRequestDto;
import com.s005.fif.dto.response.GeneAIResponseRecipeTempDto;
import com.s005.fif.dto.request.GeneAIRecipeImageRequestDto;
import com.s005.fif.service.GeneAIService;
import com.s005.fif.service.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/generate-AI")
@RequiredArgsConstructor
public class GeneAIController {

    private final GeneAIService geneAIService;
    private final GeneAIRecipeScheduler geneAIRecipeScheduler;
    private final RecipeService recipeService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "대화형 레시피 추천")
    public Flux<String> streamDataFromAI(@Parameter(hidden = true) MemberDto memberDto, GeneAIPromptRequestDto geneAIPromptRequestDto) {
        // FastAPI에서 받아온 스트림을 그대로 클라이언트에 전송
//        return geneAIService.getStreamDataFromAI(memberDto, geneAIPromptRequestDto);
        ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 인스턴스
        final String[] temp = {""};


        return geneAIService.getStreamDataFromAI(memberDto, geneAIPromptRequestDto)
                .doOnNext(data -> {
                    // 데이터를 서버 메모리에 저장
                    temp[0] += data.replace("#", " ");
                })
                .doFinally(signalType -> {
                    // 스트림 처리가 종료되면(성공적으로 완료되거나 에러/취소로 종료) 실행
                    // 메모리에 저장된 데이터를 데이터베이스에 저장

                    GeneAIResponseRecipeTempDto geneAIResponseRecipeTempDto = null;
                    Gson gson = new Gson();
                    System.out.println(temp[0]);
                    geneAIResponseRecipeTempDto = gson.fromJson(temp[0], GeneAIResponseRecipeTempDto.class);
                    geneAIService.updateGeneAIRecipe(memberDto, geneAIPromptRequestDto.getRecipeId(), geneAIResponseRecipeTempDto);

                });
    }

    @GetMapping("/new-recipe-id")
    @Operation(summary = "더미 레시피 생성해서 레시피 아이디 반환")
    public Response getNewRecipeId(@Parameter(hidden = true) MemberDto memberDto) {
        return new Response("recipeId", recipeService.getNewRecipeId(memberDto.getMemberId()));
    }

    @PostMapping("/generate-recipe-image-with-name")
    @Operation(summary = "레시피 이름 업데이트 및 이미지 생성 후 저장")
    public Response generateRecipeImageWithName(@RequestBody @Valid GeneAIRecipeImageRequestDto dto) throws IOException {
        recipeService.updateRecipeName(dto.getRecipeId(), dto.getRecipeName());
        return new Response("imgUrl", recipeService.generateAndSaveImage(dto.getRecipeId()));
    }
    
    @PostMapping("/generate-recipes")
    public Response generateRecipes() {
        geneAIRecipeScheduler.generateRecipe();
        return new Response("message", "레시피 생성 요청이 전송되었습니다.");
    }

}
