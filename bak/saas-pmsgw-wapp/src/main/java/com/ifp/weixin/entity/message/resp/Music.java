package com.ifp.weixin.entity.message.resp;

/**
 * 音乐
 * 
 */
public class Music {
	/**
	 * 音乐名称
	 */
	private String Title;
	
	/**
	 * 音乐描述
	 */
	private String Description;
	
	/**
	 * 音乐链接
	 */
	private String MusicUrl;
	
	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 */
	private String HQMusicUrl;
	
	/**
	 * 缩略图的媒体id，通过上传多媒体文件，得到的id
	 */
	private String ThumbMediaId;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMusicUrl() {
		return MusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	
}
