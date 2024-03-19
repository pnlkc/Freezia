package com.s005.fif.dto.response;

import java.util.List;

import com.s005.fif.entity.Gender;
import com.s005.fif.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDetailResponseDto {
	private Boolean onboardYn;
	private String name;
	private Gender gender;
	private String imgUrl;
	private Integer stress;
	private Integer bloodOxygen;
	private Integer sleep;
	private String preferMenu;
	private List<Integer> diseases;
	private List<Integer> dislikeIngredients;
	private Integer fridgeId;

	public static MemberDetailResponseDto fromEntity(Member m, List<Integer> diseaseIds, List<Integer> dislikeIngredientIds) {
		return MemberDetailResponseDto.builder()
			.onboardYn(m.getOnboardYn())
			.name(m.getName())
			.gender(m.getGender())
			.imgUrl(m.getImgUrl())
			.stress(m.getStress())
			.bloodOxygen(m.getBloodOxygen())
			.sleep(m.getSleep())
			.preferMenu(m.getPreferMenu())
			.diseases(diseaseIds)
			.dislikeIngredients(dislikeIngredientIds)
			.fridgeId(m.getFridge().getFridgeId())
			.build();
	}
}
