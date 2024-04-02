package com.s005.fif.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.s005.fif.entity.Member;
import com.s005.fif.entity.Recipe;
import com.s005.fif.entity.RecipeStep;
import com.s005.fif.entity.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class GeneAIResponseRecipeTempDto {

    private String reply;
    private List<GeneAIRecipeDto> recipeList;
    private List<String> recommendList;

    @Getter
    @NoArgsConstructor
    @ToString
    public static class GeneAIRecipeDto {
        private String name;
        private List<GeneAIIngredientDto> ingredientList;
        private List<GeneAIIngredientDto> seasoningList;
        private String cookTime;
        private String calorie;
        private String servings;
        private String recipeType; // ex) 볶음 요리
        private List<GeneAIRecipeStepDto> recipeSteps;

        public static Recipe toEntity(
                GeneAIResponseRecipeTempDto.GeneAIRecipeDto r,
                Member member,
                String imgUrl,
                String recommendDesc,
                Integer recommendType
        ) {
            List<String> ingredientList = r.getIngredientList().stream().map(GeneAIResponseRecipeTempDto.GeneAIRecipeDto::ingredientInfo).toList();
            List<String> seasoningList = r.getSeasoningList().stream().map(GeneAIResponseRecipeTempDto.GeneAIRecipeDto::ingredientInfo).toList();
            return Recipe.builder()
                    .member(member)
                    .name(r.getName())
                    .cookTime(Util.extractDigit(r.getCookTime()))
                    .calorie(setDefaultCalorie(Util.extractDigit(r.getCalorie())))
                    .ingredientList(String.join(",", ingredientList))
                    .seasoningList(String.join(",", seasoningList))
                    .imgUrl(imgUrl)
                    .saveYn(false)
                    .completeYn(false)
                    .recommendType(recommendType)
                    .recommendDesc(recommendDesc)
                    .recipeTypes(r.getRecipeType())
                    .serving(1)
                    .build();
        }

        private static int setDefaultCalorie(int calorie) {
            return calorie == 0 ? 250 : calorie;
        }

        public static String ingredientInfo(GeneAIIngredientDto ingredient) {
            String amounts = ingredient.getAmounts();
            String unit = ingredient.getUnit();

            // amounts가 "조금", "약간"과 같이 숫자가 아닐 경우 1t로 설정
            if (amounts.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
                amounts = "1";
                unit = "t";
            }

            return ingredient.getName() + ":" + amounts + ":" + unit;
        }
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class GeneAIIngredientDto {
        private String name;
        private String amounts;
        private String unit;

    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class GeneAIRecipeStepDto {
        private String type;
        private String description;
        private String name;
        private String duration;
        private String tip;
        private String timer;

        public static RecipeStep toEntity(GeneAIRecipeStepDto rStep, Recipe recipe, int stepNumber) {
            return RecipeStep.builder()
                    .recipe(recipe)
                    .stepNumber(stepNumber)
                    .description(rStep.getDescription())
                    .type(Type.from(rStep.type))
                    .tip(rStep.tip)
                    .timer((Util.extractDigit(rStep.getTimer())) * 60)
                    .build();
        }
    }


}
