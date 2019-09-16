package com.saas.biz.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.saas.biz.properties.WxProperties;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;

/**
 * @author ylxia
 * @version 1.0
 * @package com.woawi.server.config
 * @date 15/11/21
 */
@Configuration
@EnableConfigurationProperties(WxProperties.class)
public class WxConfiguration {

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
    public WxMaConfig wxMaConfig() {
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(info.getMiniappId());
        config.setSecret(info.getMinisecret());
        return config;
    }
    @Bean
    public WxMpService wxMpService(WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage) {
        WxMpService wxService = new WxMpServiceImpl();
        wxService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);
        return wxService;
    }
    
    @Bean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }
}