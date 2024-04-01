package com.s005.fif.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneAIPromptRequestDto {
    private String ingredients;
    private String diseases;
    private String dislikeIngredients;
    private String prompt;
    private Integer recipeId;
}
