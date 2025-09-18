package com.authservice.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

	
	@ExceptionHandler(Exception.class)
	public void exceptionHadler(Exception e) {
		
		System.out.println("caught exception");
		System.out.println(e.getClass().getName());
	}
}
