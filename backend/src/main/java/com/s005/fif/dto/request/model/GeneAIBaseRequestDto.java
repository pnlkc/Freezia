package com.s005.fif.dto.request.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GeneAIBaseRequestDto {
    private String ingredients;
    private String diseases;
    private String dislikeIngredients;
}
