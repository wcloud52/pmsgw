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
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.service.NodejsSysUserService;
import com.saas.common.BaseResponse;

import io.jsonwebtoken.Jwts;

/**
 * 登录处理
 * 
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	protected NodejsSysUserService nodejsSysUserService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String listInput(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		return "login";
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> login(@RequestParam(value = "loginName") String loginName,
			@RequestParam(value = "password") String password, HttpServletRequest request,
			HttpServletResponse response) {
		// 设置登录 COOKIE
		if (log.isDebugEnabled())
			log.debug(LoginController.class + "/doLogin->" + loginName);

		NodejsSysUser nodejsSysUser = nodejsSysUserService.selectOneByLoginName(loginName);

		if (nodejsSysUser != null && password.equals(nodejsSysUser.getPassword())) {
			String id=nodejsSysUser.getId();
			
			SSOToken st = SSOToken.create();
			st.setIp(request);
			st.setId(id);
			st.setIssuer(loginName);
			st.setJwtBuilder(Jwts.builder().claim("nodejsSysUser", nodejsSysUser));
			SSOHelper.setCookie(request, response,st, false);
			return BaseResponse.ToJsonResult(1);
		} else {
			SSOHelper.setCookie(request, response, SSOToken.create().setIp(request).setId(1000).setIssuer(loginName),
					false);
			return BaseResponse.ToCustomError(-1, "用户名或者密码不对", -1);
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

		SSOHelper.clearLogin(request, response);
		return "redirect:login";
	}
}