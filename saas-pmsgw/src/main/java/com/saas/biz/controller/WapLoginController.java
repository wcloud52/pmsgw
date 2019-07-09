package com.saas.biz.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 移动端相关操作
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/waplogin")
public class WapLoginController {
	private static final Logger loger = LoggerFactory.getLogger(WapLoginController.class); 
	
	/**
	 * get登录页
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/bind")
	public ModelAndView login(HttpServletRequest req) {
		loger.info("/login/navigation[method=get]");
		return new ModelAndView("mobile/login");
	}
	

}
