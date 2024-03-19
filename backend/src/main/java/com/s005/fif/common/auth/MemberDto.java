package com.s005.fif.common.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
	private final Integer memberId;
	private final Integer fridgeId;
}
