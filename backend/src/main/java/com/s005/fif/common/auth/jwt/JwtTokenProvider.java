package com.s005.fif.common.auth.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.s005.fif.common.auth.MemberDto;
import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

	private final SecretKey KEY;
	private final long ACCESS_TOKEN_EXPIRE_TIME_MILLI_SEC;
	private final long REFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC;
	private final String AUTHORIZATION_TYPE;
	private final String REFRESH_TOKEN_NAME;

	/**
	 * JwtTokenProvider 생성자, 설정값 초기화
	 * */
	public JwtTokenProvider(
		@Value("${JWT_SECRET_KEY}") String secret,
		@Value("${ACCESS_TOKEN_EXPIRE_TIME_MILLI_SEC}") long accessTokenExpireTimes,
		@Value("${REFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC}") long refreshTokenExpireTimes,
		@Value("${AUTHORIZATION_TYPE}") String authType,
		@Value("${AUTHORIZATION_HEADER_NAME}") String authName,
		@Value("${REFRESH_TOKEN_NAME}") String refreshTokenName) {
		this.KEY = Keys.hmacShaKeyFor(secret.getBytes());
		this.ACCESS_TOKEN_EXPIRE_TIME_MILLI_SEC = accessTokenExpireTimes;
		this.REFRESH_TOKEN_EXPIRE_TIME_MILLI_SEC = refreshTokenExpireTimes;
		this.AUTHORIZATION_TYPE = authType;
		this.REFRESH_TOKEN_NAME = refreshTokenName;
	}

	public Jwt createJwt(MemberDto memberDto) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("memberId", memberDto.getMemberId());
		claims.put("fridgeId", memberDto.getFridgeId());
		return createJwt(claims);
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
	 * claims 정보와 만료 일자로 jwt 토큰 문자열 생성
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

	/**
	 * 토큰 문자열을 Authorization 헤더 값으로 반환
	 * */
	public String toAuthorizationHeader(String token) {
		return AUTHORIZATION_TYPE + " " + token;
	}

	public String extract(HttpServletRequest httpServletRequest) {
		String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorization == null || !isAuthorizationToken(authorization))
			throw new CustomException(ExceptionType.TOKEN_NOT_VALID);
		return authorization.substring(AUTHORIZATION_TYPE.length() + 1);
	}

	/**
	 * Authorization 헤더 값이 유효한 형식인지 확인
	 * */
	public boolean isAuthorizationToken(String authorization) {
		return authorization.startsWith(AUTHORIZATION_TYPE + " ");
	}

	/**
	 * 토큰의 데이터를 MemberDto 클래스로 매핑
	 * */
	public MemberDto toMemberDto(String token) {
		Claims claims = getClaims(token);
		Integer memberId = claims.get("memberId", Integer.class);
		Integer fridgeId = claims.get("fridgeId", Integer.class);
		return MemberDto.builder()
			.memberId(memberId)
			.fridgeId(fridgeId)
			.build();
	}

}
