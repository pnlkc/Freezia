package com.s005.fif.common.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.s005.fif.common.auth.jwt.JwtTokenProvider;
import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(MemberDto.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest httpServletRequest = (HttpServletRequest)webRequest.getNativeRequest();
		String token = jwtTokenProvider.extract(httpServletRequest);
		MemberDto memberDto;
		try {
			memberDto = jwtTokenProvider.toMemberDto(token);
		} catch (MalformedJwtException | UnsupportedJwtException e) {
			throw new CustomException(ExceptionType.TOKEN_NOT_VALID);
		} catch (ExpiredJwtException e) {
			throw new CustomException(ExceptionType.TOKEN_EXPIRED);
		}

		return memberDto;
	}
}
