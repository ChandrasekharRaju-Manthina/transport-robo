package com.allstate.trobo.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppWideExceptionHandler {

	final static Logger logger = Logger.getLogger(AppWideExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public String duplicateSpittleHandler(Exception e) {
		logger.error("Ëxception occured. ", e);
		return "error";
	}

}