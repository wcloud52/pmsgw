package com.saas.kisso;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.common.SSOConstants;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.security.token.SSOToken;
import com.baomidou.kisso.web.interceptor.SSOSpringInterceptor;


/**
 * WEB 初始化相关配置
 * @author tanjun
 *
 */
@ControllerAdvice
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        /*registry.addInterceptor(new HandlerInterceptorAdapter() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                     Object handler) throws Exception {
            	 if (handler instanceof ResourceHttpRequestHandler) {
            		 SSOToken ssoToken = SSOHelper.getSSOToken(request);
                     if (ssoToken == null) {
                    	 if (HttpUtil.isAjax(request)) {
         					HttpUtil.ajaxStatus(response, 302, "session expires.");
         					return false;
         				} else {
         					SSOHelper.clearRedirectLogin(request, response);
         					return false;
         				}
                     } else {
         				
         				 * 正常请求，request 设置 token 减少二次解密
         				 
                         request.setAttribute(SSOConstants.SSO_TOKEN_ATTR, ssoToken);
                     }
            	 }
                return true;
            }
        })
        //.addPathPatterns("/index.html","/main.html")
        .excludePathPatterns("/login.html","/swagger-ui.html");*/
     // kisso 拦截器配置
       registry.addInterceptor(new SSOSpringInterceptor())
       .addPathPatterns("/**")
       .excludePathPatterns(
    		   "/wap/**",
    		   "/wap3/**",
    		   "/wap/racedetaillist/**",
    		   "/wapIndex/**",
    		   "/waplogin/**",
    		   "/mp/**",   		   
    		   "/storage/**",
    		   "/wx/**",
    		   
    		   "/plugins/**",
    		   "/css/**",
    		   "/datas/**",
    		   "/imsges/**",
    		   "/js/**",
    		   "/pagejs/**",
    		   "/error",
    		   "/login","/doLogin","/login.html","/swagger-resources/**","/weixinController/**");
    }
}
