package com.ifp.weixin.entity.customer;

/**
 * 视频消息对象
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class Video {
	/**
	 * 视频消息的标题
	 */
	private String title;
	
	/**
	 * 视频消息的描述
	 */
	private String description;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Video(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

	public Video() {
		super();
	}
	
}
