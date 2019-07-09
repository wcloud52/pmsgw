package com.ifp.weixin.entity.message.req;

/**
 * 语音消息
 * 
 */
public class VoiceRecongnitionMessage extends VoiceMessage {


	/**
	 * 语音识别结果
	 */
	private String Recongnition;

	public String getRecongnition() {
		return Recongnition;
	}

	public void setRecongnition(String recongnition) {
		Recongnition = recongnition;
	}

	

}
