package com.ifp.weixin.entity.message.resp;

/**
 * 图片消息
 * 
 */
public class ImageMessage extends BaseMessage{
	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image Image) {
		this.Image = Image;
	}

	

	
	
}
