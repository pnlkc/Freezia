package com.s005.fif.dto.fcm;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmSendDto {

	@Setter
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
