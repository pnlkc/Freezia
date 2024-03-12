package com.s005.fif.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.s005.fif.common.response.Response;
import com.s005.fif.common.response.ResponseFail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<Response> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getExceptionType().getCode())
                .body(new ResponseFail(String.valueOf(e.getExceptionType().getCode()), e.getMessage()));
    }
}
