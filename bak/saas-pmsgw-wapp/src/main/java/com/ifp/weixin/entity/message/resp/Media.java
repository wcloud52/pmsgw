package com.ifp.weixin.entity.message.resp;

/**
 * 多媒体消息<br>
 * 图片消息、语音消息直接用此类
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class Media {
	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private String mediaId;

	public Media()
	{
		super();
	}
	public Media(String mediaId)
	{
		super();
		this.mediaId=mediaId;
	}
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
