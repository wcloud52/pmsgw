package com.saas.biz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/wapIndex")
public class WapIndexController {


	/**
	 * index
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) {
		
		return new ModelAndView("mobile/index");
	}
	@RequestMapping("/detail")
	public ModelAndView detail(HttpServletRequest req, HttpServletResponse resp) {
		
		return new ModelAndView("mobile/detail");
	}
}
