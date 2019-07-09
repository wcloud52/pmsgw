package com.saas.common.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
@Component
public class ExceptionHandler implements HandlerExceptionResolver,Ordered {

	private final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		log.error("异常", ex);
		// 输出错误Json
		ModelAndView mav = new ModelAndView();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", -1);
		result.put("timestamp", new Date());
		result.put("status",500);
		result.put("message", "处理异常，请稍后再试");
		result.put("data", ex.getMessage());
		result.put("exception", ex.getClass().getName());
		result.put("path", request.getRequestURI());
		view.setAttributesMap(result);
		mav.setView(view);
	
		return mav;
	}
	@Override
	public int getOrder() {
		return 0;
	}
}
