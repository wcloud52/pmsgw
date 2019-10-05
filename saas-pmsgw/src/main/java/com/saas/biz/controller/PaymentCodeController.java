package com.saas.biz.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.pojo.PaymentCode;
import com.saas.biz.service.PaymentCodeService;
import com.saas.biz.util.SnGenerator;
import com.saas.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 微信用户管理
 * @author sunqiu
 *
 */
@Controller
@RequestMapping("/paymentCode")
public class PaymentCodeController {

	private static final Logger log = LoggerFactory.getLogger(PaymentCodeController.class);

	@Autowired
	private PaymentCodeService paymentCodeService;

	
	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(ModelMap model, HttpServletRequest request) {
		SSOToken ssoToken = SSOHelper.getSSOToken(request);

		if (ssoToken != null) {
			NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);
			PaymentCode item = paymentCodeService.selectOneById(nodejsSysUser.getCote_id());

			model.put("item", item);
		}

        
		return "paymentcode/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> updateOperation(PaymentCode body, HttpServletRequest request) {
		if (log.isDebugEnabled())
			log.debug(PaymentCodeController.class + "/update->" + JSON.toJSONString(body));
		PaymentCode record = body;

		int result=0;
		SSOToken ssoToken = SSOHelper.getSSOToken(request);
		String cote_id="";
		if (ssoToken != null) {
			NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);
			cote_id=nodejsSysUser.getCote_id();
		}
		paymentCodeService.deleteById(cote_id);
			record.setId(cote_id);
			record.setCote_id(cote_id);
			record.setCreat_time(new Date());
			record.setModify_time(new Date());
			result=paymentCodeService.insert(record);


		return BaseResponse.ToJsonResult(result);
	}

	
}