package com.s005.fif.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmSendDto {

	private String token;

	private String title;

	private String body;

	private Object data;

	@Builder(toBuilder = true)
	public FcmSendDto(String token, String title, String body, Object data) {
		this.token = token;
		this.title = title;
		this.body = body;
		this.data = data;
	}
}
