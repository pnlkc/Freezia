package com.s005.fif.dto.fcm;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmTokenDto {
	private DeviceType type;
	private String token;
}
