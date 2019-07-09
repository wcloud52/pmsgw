package com.ifp.weixin.entity.message.resp;

/**
 * 音乐消息
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class MusicMessage extends BaseMessage {
	private Music Music;


	public MusicMessage() {
		super();
	}

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
	
}