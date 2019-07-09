package com.saas.biz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.biz.pojo.NodejsSysUser;

@Controller
@RequestMapping("/")
public class IndexController {

	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	
	    @RequestMapping(value ={"","/","index"}, method = RequestMethod.GET)
	    public String listInput(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
	    	 SSOToken ssoToken = SSOHelper.getSSOToken(request);
	    	 if (log.isDebugEnabled())
	 			log.debug(IndexController.class + "/->" + JSON.toJSONString(ssoToken));
	    	 
             if (ssoToken != null) {
            	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);
            			 
            	 model.put("currentUser", nodejsSysUser);
            	/* model.put("currentUserName", nodejsSysUser.getUserName());
            	 model.put("currentUserAccount", nodejsSysUser.getLoginName());
            	
            	 model.put("currentUserType", nodejsSysUser.getUserType());*/
             }
	        return "index";
	    }
	    
	    @RequestMapping(value ={"error"}, method = RequestMethod.GET)
	    public String error(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
	    	 
	        return "error";
	    }
}