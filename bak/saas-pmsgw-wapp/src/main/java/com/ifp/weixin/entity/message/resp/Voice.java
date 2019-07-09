package com.ifp.weixin.entity.message.resp;

/**
 * 语音
 * 
 */
public class Voice {
	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private String MediaId;

	public Voice()
	{
		super();
	}
	public Voice(String MediaId)
	{
		super();
		this.MediaId=MediaId;
	}
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String MediaId) {
		this.MediaId = MediaId;
	}
	
}
