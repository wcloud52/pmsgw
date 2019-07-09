package com.saas.biz.controller;

import java.io.IOException;
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
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	 @Autowired
	    protected HttpServletRequest request;
	    @Autowired
	    protected HttpServletResponse response;
	    
	    @RequestMapping(value ="/login", method = RequestMethod.GET)
	    public String listInput(ModelMap model) {
	        return "login";
	    }
	    
	    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	    public String login(@RequestParam(value = "userName") String userName,
				@RequestParam(value = "password") String password) {
	        // 设置登录 COOKIE
		 if (log.isDebugEnabled())
				log.debug(LoginController.class + "/doLogin->"+userName );
	        SSOHelper.setCookie(request, response, SSOToken.create().setIp(request).setId(1000).setIssuer(userName), false);
	        return "redirect:index";
	    }
	    
	    @RequestMapping("/logout")
	    public String logout() throws IOException {
	       
			SSOHelper.clearLogin(request, response);
			return "redirect:login";
	    }
}