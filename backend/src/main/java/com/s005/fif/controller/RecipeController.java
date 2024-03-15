package com.s005.fif.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s005.fif.common.response.Response;
import com.s005.fif.service.RecipeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/test")
    public String test() {
        return "Hello, world";
    }

    @GetMapping("/{recipeId}")
    public Response getRecipe(@RequestHeader(value = "Authorization", required = false) String token,
        @PathVariable Integer recipeId) {
        return new Response("recipeInfo", recipeService.getRecipe(token, recipeId));
    }

    @GetMapping("/{recipeId}/steps")
    public Response getRecipeSteps(@RequestHeader(value = "Authorization", required = false) String token,
        @PathVariable Integer recipeId) {
        return new Response("recipeSteps", recipeService.getRecipeSteps(token, recipeId));
    }
}