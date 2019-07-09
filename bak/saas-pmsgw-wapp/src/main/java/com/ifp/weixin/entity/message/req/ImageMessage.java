package com.ifp.weixin.entity.message.req;
/**
 * 图片消息
 * 
 */
public class ImageMessage extends MediaMessage{

	/**
	 * 图片链接
	 */
	private String PicUrl;
	
	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		this.PicUrl = picUrl;
	}

	public ImageMessage(String picUrl) {
		super();
		this.PicUrl = picUrl;
	}
	
}
