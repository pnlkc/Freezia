package com.s005.fif.common.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.response.ResponseFail;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@WebFilter
@Order(0)
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	/**
	 * Filter 단위에서 CustomException 을 잡아주는 필터
	 * */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try{
			filterChain.doFilter(request, response);
		} catch (CustomException e){
			response.setStatus(e.getExceptionType().getCode());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(new ResponseFail(String.valueOf(e.getExceptionType().getCode()), e.getMessage()).toString());
			response.getWriter().flush();
		}
	}
}
