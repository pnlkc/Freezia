package com.s005.fif.dto.response;

import com.s005.fif.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSimpleResponseDto {
	Integer memberId;
	String name;
	String imgUrl;

	public static MemberSimpleResponseDto fromEntity(Member member) {
		return MemberSimpleResponseDto.builder()
			.memberId(member.getMemberId())
			.name(member.getName())
			.imgUrl(member.getImgUrl())
			.build();
	}
}
