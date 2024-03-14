package com.s005.fif.common.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class JwtUtils {
	private final long ACCESS_TOKEN_EXPIRE_TIME_MILLI_SEC;
	private final long REFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC;
	private final String AUTHORIZATION_TYPE;
	private final String AUTHORIZATION_HEADER_NAME;
	private final String REFRESH_TOKEN_NAME;

	public JwtUtils(
		@Value("${ACCESS_TOKEN_EXPIRE_TIME_MILLI_SEC}") long accessTokenExpireTimes,
		@Value("${REFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC}") long refreshTokenExpireTimes,
		@Value("${AUTHORIZATION_TYPE}") String authType,
		@Value("${AUTHORIZATION_HEADER_NAME}") String authName,
		@Value("${REFRESH_TOKEN_NAME}") String refreshTokenName) {
		this.ACCESS_TOKEN_EXPIRE_TIME_MILLI_SEC = accessTokenExpireTimes;
		this.REFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC = refreshTokenExpireTimes;
		this.AUTHORIZATION_TYPE = authType;
		this.AUTHORIZATION_HEADER_NAME = authName;
		this.REFRESH_TOKEN_NAME = refreshTokenName;
	}

	public String toAuthHeader(String token) {
		return AUTHORIZATION_TYPE + " " + token;
	}
}
