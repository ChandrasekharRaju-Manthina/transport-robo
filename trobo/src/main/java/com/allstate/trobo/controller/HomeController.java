package com.allstate.trobo.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.allstate.trobo.exception.ApplicationException;

@Controller
@RequestMapping({ "/", "home" })
public class HomeController {
	
	final static Logger logger = Logger.getLogger(HomeController.class);

	@RequestMapping(method = GET)
	public String home(Model model) {
		logger.warn("Test message");
		return "home";
	}

	@RequestMapping(value = "exception", method = GET)
	public String exception() {
		throw new ApplicationException("Testing exception");
	}

}
