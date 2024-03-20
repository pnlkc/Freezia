package com.s005.fif.dto.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmMessageDto {
	private boolean validateOnly;
	private FcmMessageDto.Message message;

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Message {
		private FcmMessageDto.Notification notification;
		private String token;
		private Object data;
	}

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Notification {
		private String title;
		private String body;
		private String image;
	}
}
