package com.ifp.weixin.entity.message.resp;

/**
 * 视频
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class Video {
	private String MediaId;
	/**
	 * 视频消息的标题
	 */
	private String Title;
	
	/**
	 * 视频消息的描述
	 */
	private String Description;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

	public Video(String title, String description) {
		super();
		this.Title = title;
		this.Description = description;
	}

	public Video() {
		super();
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
}
