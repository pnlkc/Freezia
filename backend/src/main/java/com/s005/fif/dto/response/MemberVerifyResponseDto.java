package com.s005.fif.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberVerifyResponseDto {
	private boolean isValid;
}
