package com.s005.fif.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

	private final SecretKey KEY;
	private final long ACCESS_TOKEN_EXPIRE_TIME_MILLI_SEC;
	private final long REFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC;

	/**
	 * JwtTokenProvider 생성자, 설정값 초기화
	 * */
	public JwtTokenProvider(
		@Value("${JWT_SECRET_KEY}") String secret,
		@Value("${ACCESS_TOKEN_EXPIRE_TIME_MILLI_SEC}") long accessTokenExpireTimes,
		@Value("${REFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC}") long refreshTokenExpireTimes) {
		this.KEY = Keys.hmacShaKeyFor(secret.getBytes());
		this.ACCESS_TOKEN_EXPIRE_TIME_MILLI_SEC = accessTokenExpireTimes;
		this.REFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC = refreshTokenExpireTimes;
	}

	/**
	 * claims 정보로 액세스 토큰, 리프레쉬 토큰을 새롭게 만들고 이를 포함하는 Jwt 인스턴스 반환
	 * */
	public Jwt createJwt(Map<String, ?> claims) {
		String accessToken = createToken(claims, getExpireDateAccessToken());
		String refreshToken = createToken(new HashMap<>(), getExpireDateRefreshToken());
		return Jwt.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	/**
	 * claims 정보로 jwt 토큰 문자열 생성
	 * */
	public String createToken(Map<String, ?> claims, Date expireDate) {
		return Jwts.builder()
			.claims(claims)
			.expiration(expireDate)
			.signWith(KEY)
			.compact();
	}

	/**
	 * 토큰 문자열을 해석해서 Claims 인스턴스 반환
	 * */
	public Claims getClaims(String token) {
		return Jwts.parser()
			.verifyWith(KEY)
			.build()
			.parseSignedClaims(token).getPayload();
	}

	public Date getExpireDateAccessToken() {
		return new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME_MILLI_SEC);
	}

	public Date getExpireDateRefreshToken() {
		return new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC);
	}

}
