package com.allstate.trobo.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.allstate.trobo.exception.ApplicationException;

@Controller

public class ViewController {
	
	final static Logger logger = Logger.getLogger(ViewController.class);

	@RequestMapping({ "/", "home" })
	public String home(Model model) {
		logger.warn("Test message. Welcome to home!");
		return "home";
	}

	@RequestMapping(value = "exception", method = GET)
	public String exception() {
		throw new ApplicationException("Testing exception");
	}
	
	@RequestMapping("addressHome")
	public String addressHome(Model model) {
		return "addressHome";
	}

}
