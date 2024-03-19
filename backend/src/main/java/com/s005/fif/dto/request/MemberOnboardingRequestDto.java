package com.s005.fif.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberOnboardingRequestDto {
	private String preferMenu;
	private List<Integer> diseases;
	private List<Integer> dislikeIngredients;
}
