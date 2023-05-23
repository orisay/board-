package com.project.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.config.MyResponseEntity;
import com.project.exception.UnknownException;
import com.project.dto.MyResponseEntityDTO;

@RestControllerAdvice
public class GlobalUnknownExceptionHandler {

	@ExceptionHandler(UnknownException.class)
	public MyResponseEntity<String> handleUnknownException(UnknownException e) {
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("warn error" + e.getMessage()));

	}
}
