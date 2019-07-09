package com.ifp.weixin.entity.message.req;

/**
 * 多媒体消息<br>
 * 图片消息、语音消息直接用此类
 * 
 */
public class MediaMessage extends BaseMessage{
	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		this.MediaId = mediaId;
	}

}
