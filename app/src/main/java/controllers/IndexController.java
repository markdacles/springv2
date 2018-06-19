package com.exist.ecc.controllers;

import javax.servlet.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController{

	@RequestMapping(value="/login", method={ RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(ModelAndView modelAndView){
		modelAndView.setViewName("login");
		return modelAndView;
	}


	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home(ModelAndView modelAndView){
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping(value="/forbidden", method=RequestMethod.GET)
	public ModelAndView denied(ModelAndView modelAndView){
		modelAndView.setViewName("forbidden");
		return modelAndView;
	}
}