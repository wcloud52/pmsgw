package com.saas.biz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;

@Controller
@RequestMapping("")
public class IndexController {

	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	 @Autowired
	    protected HttpServletRequest request;
	    @Autowired
	    protected HttpServletResponse response;
	    
	    @RequestMapping(value ="/index", method = RequestMethod.GET)
	    public String listInput(ModelMap model) {
	    	 SSOToken ssoToken = SSOHelper.getSSOToken(request);
             if (ssoToken != null) {
            	 model.put("currentUserCode", ssoToken.getId());
            	 model.put("currentUserName", ssoToken.getIssuer());
            	 model.put("currentUserAccount", ssoToken.getIssuer());
            	 model.put("currentUserType", 0);
             }
	        return "index";
	    }
	    
	  
}