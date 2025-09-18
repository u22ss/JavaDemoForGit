package com.microservices1.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(Exception.class)
	public void handleException(Exception e)
	{
		System.out.println(e.getClass().getName() + " " + e.getMessage());
		System.out.println(".............................. exception caught .........................");
		
	}
}
