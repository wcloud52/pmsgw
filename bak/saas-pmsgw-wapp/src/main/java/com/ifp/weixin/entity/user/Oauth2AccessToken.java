package com.ifp.weixin.entity.user;

/**
 * 微信通用接口凭证
 * @author caspar.chen
 * @version 1.0
 */
public class Oauth2AccessToken {
	
	/**
	 *  获取到的凭证
	 */
	private String token;
	private String openid;
	
	/**
	 *  凭证有效时间，单位：秒
	 */
	private int expiresIn;
	private String refreshToken="REFRESH_TOKEN";
	private String scope="SCOPE";
	private String unionid;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}