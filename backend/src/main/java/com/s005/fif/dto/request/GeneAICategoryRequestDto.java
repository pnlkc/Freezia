package com.s005.fif.dto.request;

import com.s005.fif.dto.request.model.GeneAIBaseRequestDto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GeneAICategoryRequestDto extends GeneAIBaseRequestDto {
	private final String name;
}
