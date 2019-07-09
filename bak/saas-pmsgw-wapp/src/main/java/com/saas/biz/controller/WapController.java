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
@RequestMapping("/wap")
public class WapController {
	private static final Logger loger = LoggerFactory.getLogger(WapController.class); 
	
	@RequestMapping(method = RequestMethod.GET,value = "/bindLoft")
	public ModelAndView bindLoft(HttpServletRequest req) {
		
		return new ModelAndView("wap/bindLoft");
	}
	@RequestMapping(method = RequestMethod.GET,value = "/bindClub")
	public ModelAndView bindClub(HttpServletRequest req) {
		
		return new ModelAndView("wap/bindClub");
	}
	/**
	 * get登录页
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/bind")
	public ModelAndView bind(HttpServletRequest req) {
		
		return new ModelAndView("wap/bind");
	}
	

	@RequestMapping(method = RequestMethod.GET,value = "/racelist")
	public ModelAndView racelist(HttpServletRequest req) {
		
		return new ModelAndView("wap/racelist");
	}
	@RequestMapping(method = RequestMethod.GET,value = "/racedetaillist")
	public ModelAndView racedetaillist(HttpServletRequest req) {
		
		return new ModelAndView("wap/racedetaillist");
	}
}
