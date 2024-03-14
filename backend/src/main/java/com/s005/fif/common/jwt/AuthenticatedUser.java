package com.s005.fif.common.jwt;

import lombok.Builder;

/**
 * 사용자 액세스 토큰의 claims 데이터와 매핑하는 클래스
 * */
public record AuthenticatedUser (Integer memberId) {
	@Builder
	public AuthenticatedUser {
	}
}
