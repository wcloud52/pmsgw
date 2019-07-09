package com.ifp.weixin.entity.message.resp;

/**
 * 图片
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class Image {
	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private String MediaId;

	public Image()
	{
		super();
	}
	public Image(String MediaId)
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
