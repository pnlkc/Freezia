package com.s005.fif.entity;

import com.s005.fif.common.Constant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Fridge {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fridgeId;

	@Column(nullable = false)
	@Size(max = Constant.COMMON_TITLE_LENGTH)
	private String name;

	@Setter
	@Size(max = Constant.FCM_TOKEN_LENGTH)
	private String fridgeToken;
}
