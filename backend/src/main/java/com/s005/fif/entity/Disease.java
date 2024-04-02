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

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Disease {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer diseaseId;

	@Column(nullable = false)
	@Size(max = Constant.COMMON_TITLE_LENGTH)
	private String name;

}
