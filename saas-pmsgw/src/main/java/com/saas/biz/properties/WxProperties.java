package com.saas.biz.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ylxia
 * @version 1.0
 * @package com.woawi.wx.controller
 * @date 15/11/21
 */
@ConfigurationProperties(prefix = "wechat", ignoreUnknownFields = false)
public class WxProperties {

    private String appId;

    private String secret;

    private String token;

    private String aesKey;
    private String gameTemplateId;
    private String resultTemplateId;
    
    private String miniappId;

    private String minisecret;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public String getGameTemplateId() {
		return gameTemplateId;
	}

	public void setGameTemplateId(String gameTemplateId) {
		this.gameTemplateId = gameTemplateId;
	}

	public String getResultTemplateId() {
		return resultTemplateId;
	}

	public void setResultTemplateId(String resultTemplateId) {
		this.resultTemplateId = resultTemplateId;
	}

	public String getMiniappId() {
		return miniappId;
	}

	public void setMiniappId(String miniappId) {
		this.miniappId = miniappId;
	}

	public String getMinisecret() {
		return minisecret;
	}

	public void setMinisecret(String minisecret) {
		this.minisecret = minisecret;
	}

	
    
}