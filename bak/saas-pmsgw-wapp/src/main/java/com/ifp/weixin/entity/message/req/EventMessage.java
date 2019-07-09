package com.ifp.weixin.entity.message.req;

/**
 * 事件消息<br>
 * 图片消息、语音消息直接用此类
 * 
 */
public class EventMessage extends BaseMessage{
	
	private String EventKey;
	private String Event;
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}


}
