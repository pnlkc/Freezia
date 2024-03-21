package com.s005.fif.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.response.Response;
import com.s005.fif.dto.request.CompleteCookRequestDto;
import com.s005.fif.dto.response.CompleteCookResponseDto;
import com.s005.fif.dto.response.RecipeResponseDto;
import com.s005.fif.dto.response.RecipeSimpleResponseDto;
import com.s005.fif.dto.response.RecipeStepResponseDto;
import com.s005.fif.dto.response.ShoppingListResponseDto;
import com.s005.fif.repository.RecipeRecommendationResponseDto;
import com.s005.fif.service.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    @Operation(summary = "레시피 목록 조회")
    @ApiResponse(
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = RecipeResponseDto.class))
            )
        }
    )
    public Response getRecipes(@Parameter(hidden = true)MemberDto memberDto) {
        return new Response("recipes", recipeService.getRecipes(memberDto.getMemberId()));
    }

    @GetMapping("/history/save")
    @Operation(summary = "저장한 레시피 목록 조회")
    @ApiResponse(
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = RecipeSimpleResponseDto.class))
            )
        }
    )
    public Response getSavedRecipes(@Parameter(hidden = true)MemberDto memberDto) {
        return new Response("recipes", recipeService.getSavedRecipes(memberDto.getMemberId()));
    }

    @GetMapping("/history/complete")
    @Operation(summary = "완료한 레시피 목록 조회")
    @ApiResponse(
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = RecipeSimpleResponseDto.class))
            )
        }
    )
    public Response getCompletedRecipes(@Parameter(hidden = true)MemberDto memberDto) {
        return new Response("recipes", recipeService.getCompletedRecipes(memberDto.getMemberId()));
    }

    @GetMapping("/recommendation")
    @Operation(summary = "추천 레시피 목록 조회")
    @ApiResponse(
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = RecipeRecommendationResponseDto.class))
            )
        }
    )
    public Response getRecommendationRecipes(@Parameter(hidden = true)MemberDto memberDto) {
        return new Response("recipes", recipeService.getRecommendationRecipes(memberDto.getMemberId()));
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "레시피 상세 조회")
    @ApiResponse(
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RecipeResponseDto.class)
            )
        }
    )
    public Response getRecipe(@Parameter(hidden = true)MemberDto memberDto, @PathVariable Integer recipeId) {
        return new Response("recipeInfo", recipeService.getRecipe(memberDto.getMemberId(), recipeId));
    }

    @GetMapping("/{recipeId}/steps")
    @Operation(summary = "레시피 단계 상세 조회")
    @ApiResponse(
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RecipeStepResponseDto.class)
            )
        }
    )
    public Response getRecipeSteps(@Parameter(hidden = true)MemberDto memberDto, @PathVariable Integer recipeId) {
        return new Response("recipeSteps", recipeService.getRecipeSteps(memberDto.getMemberId(), recipeId));
    }

    @PatchMapping("/{recipeId}/save")
    @Operation(summary = "레시피 북마크 토글")
    public Response toggleSaveYn(@Parameter(hidden = true)MemberDto memberDto, @PathVariable Integer recipeId) {
        return new Response(Response.MESSAGE, recipeService.toggleSaveYn(memberDto.getMemberId(), recipeId));
    }

    @PostMapping("/{recipeId}/complete")
    @Operation(summary = "요리 기록 저장")
    public Response completeCook(@Parameter(hidden = true)MemberDto memberDto, @PathVariable Integer recipeId, @RequestBody @Valid CompleteCookRequestDto dto) {
        return new Response(Response.MESSAGE, recipeService.completeCook(memberDto.getMemberId(), recipeId, dto));
    }

    @GetMapping("/{recipeId}/complete")
    @Operation(summary = "요리 히스토리 목록 조회")
    @ApiResponse(
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = CompleteCookResponseDto.class))
            )
        }
    )
    public Response getCompleteCook(@Parameter(hidden = true)MemberDto memberDto, @PathVariable Integer recipeId) {
        return new Response("completeCooks", recipeService.getCompleteCook(memberDto.getMemberId(), recipeId));
    }

    @DeleteMapping("/complete/{completeCookId}")
    @Operation(summary = "요리 히스토리 삭제")
    public Response deleteCompleteCook(@Parameter(hidden = true)MemberDto memberDto, @PathVariable Integer completeCookId) {
        return new Response(Response.MESSAGE, recipeService.deleteCompleteCook(memberDto.getMemberId(), completeCookId));
    }
}
