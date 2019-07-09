package com.ifp.weixin.entity.message.req;

/**
 * 视频消息
 * 
 */
public class VideoMessage extends BaseMessage{
	
	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
	 */
	private String ThumbMediaId;

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.ThumbMediaId = thumbMediaId;
	}

	public VideoMessage(String thumbMediaId) {
		super();
		this.ThumbMediaId = thumbMediaId;
	}
	
	
}
