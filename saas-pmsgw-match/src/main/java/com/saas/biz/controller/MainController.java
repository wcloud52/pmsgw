package com.saas.biz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class MainController {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	 @Autowired
	    protected HttpServletRequest request;
	    @Autowired
	    protected HttpServletResponse response;
	    
	    @RequestMapping(value ="/main", method = RequestMethod.GET)
	    public String listInput(ModelMap model) {
	        return "main";
	    }
	    
	  
}