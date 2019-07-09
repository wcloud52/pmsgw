package com.saas.biz.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.saas.biz.properties.WxPayProperties;

/**
 * @author Binary Wang
 */
@Configuration
/*@ConditionalOnClass(WxPayService.class)*/
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfiguration {
	 @Autowired
  private WxPayProperties properties;

 /* @Autowired
  public WxPayConfiguration(WxPayProperties properties) {
    this.properties = properties;
  }*/

  /*@Autowired
	private WxPayProperties wxPayProperties;*/
  
  @Bean
  //@ConditionalOnMissingBean
  public WxPayService wxService() {
	 System.out.println(JSON.toJSONString(properties));
    WxPayConfig payConfig = new WxPayConfig();
   /* payConfig.setAppId(this.properties.getAppId());
    payConfig.setMchId(this.properties.getMchId());
    payConfig.setMchKey(this.properties.getMchKey());
    payConfig.setKeyPath(this.properties.getKeyPath());*/
    	    
    payConfig.setAppId("wx6fe226626254fab1");
    payConfig.setMchId("1498533382");
    payConfig.setMchKey("5cf0ee38d874bfd860e75defa6def730");
    payConfig.setKeyPath("classpath:cert/apiclient_cert.p12");

    // 可以指定是否使用沙箱环境
    payConfig.setUseSandboxEnv(false);

    WxPayService wxPayService = new WxPayServiceImpl();
    wxPayService.setConfig(payConfig);
    return wxPayService;
  }

}
