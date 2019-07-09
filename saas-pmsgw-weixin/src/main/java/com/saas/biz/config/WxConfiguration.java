package com.saas.biz.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.saas.biz.properties.WxPayProperties;
import com.saas.biz.properties.WxProperties;

/**
 * @author ylxia
 * @version 1.0
 * @package com.woawi.server.config
 * @date 15/11/21
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties({WxProperties.class,WxPayProperties.class})
public class WxConfiguration {

	@Autowired
	private WxPayProperties wxPayProperties;
	
	@Autowired
    private WxProperties info;

    @Bean
    public WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage() {
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(info.getAppId()); // 设置微信公众号的appid
        config.setSecret(info.getSecret()); // 设置微信公众号的app corpSecret
        config.setToken(info.getToken()); // 设置微信公众号的token
        //config.setAesKey(info.getAesKey()); // 设置微信公众号的EncodingAESKey
        return config;
    }


    @Bean
    public WxMpService wxMpService(WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage) {
        WxMpService wxService = new WxMpServiceImpl();
        wxService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);
        return wxService;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxService() {
      WxPayConfig payConfig = new WxPayConfig();
      payConfig.setAppId(StringUtils.trimToNull(this.wxPayProperties.getAppId()));
      payConfig.setMchId(StringUtils.trimToNull(this.wxPayProperties.getMchId()));
      payConfig.setMchKey(StringUtils.trimToNull(this.wxPayProperties.getMchKey()));
      payConfig.setSubAppId(StringUtils.trimToNull(this.wxPayProperties.getSubAppId()));
      payConfig.setSubMchId(StringUtils.trimToNull(this.wxPayProperties.getSubMchId()));
      payConfig.setKeyPath(StringUtils.trimToNull(this.wxPayProperties.getKeyPath()));

      // 可以指定是否使用沙箱环境
      payConfig.setUseSandboxEnv(false);

      WxPayService wxPayService = new WxPayServiceImpl();
      wxPayService.setConfig(payConfig);
      
   

      return wxPayService;
    }
    
}