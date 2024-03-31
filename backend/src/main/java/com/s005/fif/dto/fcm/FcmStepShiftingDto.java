package com.s005.fif.dto.fcm;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmStepShiftingDto {

	private Integer type;

	private Integer step;

	private Integer sender;
}
