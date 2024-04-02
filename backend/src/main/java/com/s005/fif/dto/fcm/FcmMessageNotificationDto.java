package com.s005.fif.dto.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmMessageNotificationDto {
	private boolean validateOnly;
	private FcmMessageNotificationDto.Message message;

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Message {
		private String token;
		private Object data;
	}

}
