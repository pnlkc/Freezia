package com.s005.fif.dto.fcm;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FCMStepShiftingRequestDto {
	@NotNull
	private Integer sender;
}
