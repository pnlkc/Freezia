package com.s005.fif.common.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;
import com.s005.fif.common.jwt.AuthenticatedUser;
import com.s005.fif.common.jwt.JwtTokenProvider;
import com.s005.fif.service.MemberService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@WebFilter
@Order(1)
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final String[] whiteListUris = new String[] {"/api/members"};

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberService memberService;

	/**
	 * 사용자 인증 처리 - 유효한 토큰인지, 유효한 사용자인지 확인
	 * */
	@Override
	public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		FilterChain chain) throws
		IOException, ServletException, CustomException {
		if (checkWhiteList(httpServletRequest.getRequestURI())) {
			chain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		if (!isContainToken(httpServletRequest)) {
			throw new CustomException(ExceptionType.TOKEN_NOT_EXIST);
		}
		try {
			String token = getToken(httpServletRequest);
			AuthenticatedUser authenticateUser = jwtTokenProvider.toAuthenticatedUser(token);
			if (!memberService.verifyMember(authenticateUser)) {
				throw new CustomException(ExceptionType.USER_NOT_FOUND);
			}
			chain.doFilter(httpServletRequest, httpServletResponse);
		} catch (JsonParseException | MalformedJwtException | UnsupportedJwtException e) {
			throw new CustomException(ExceptionType.TOKEN_NOT_VALID);
		} catch (ExpiredJwtException e) {
			throw new CustomException(ExceptionType.TOKEN_EXPIRED);
		}
	}

	private boolean checkWhiteList(String uri) {
		return PatternMatchUtils.simpleMatch(whiteListUris, uri);
	}

	private boolean isContainToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		return authorization != null && jwtTokenProvider.isAuthorizationToken(authorization);
	}

	private String getToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		return jwtTokenProvider.toJwtToken(authorization);
	}

}
