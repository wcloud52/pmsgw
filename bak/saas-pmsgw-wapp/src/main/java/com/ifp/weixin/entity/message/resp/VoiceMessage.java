package com.ifp.weixin.entity.message.resp;

/**
 * 语音消息
 * 
 */
public class VoiceMessage extends BaseMessage{
	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}

	
}
