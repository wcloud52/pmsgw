package com.ifp.weixin.entity.message.req;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 用户request请求消息管理类
 * 
 */
public class RequestMessageManager {

	private Map<String, String> requestMap;
	
	public Map<String, String> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, String> requestMap) {
		this.requestMap = requestMap;
	}
	public static BaseMessage getBaseMessage(Map<String, String> map) throws Exception
	{
		BaseMessage baseMessage = null;
		
		String jsonStr=JSON.toJSONString(map);
		if(map.get("MsgType")!=null)
		{
			String msgType=map.get("MsgType");
			if("text".equals(msgType))
			{
				baseMessage=JSON.parseObject(jsonStr, TextMessage.class);
			}
			else if("image".equals(msgType))
			{
				baseMessage=JSON.parseObject(jsonStr, ImageMessage.class);
			}
			else if("voice".equals(msgType))
			{
				baseMessage=JSON.parseObject(jsonStr, VoiceMessage.class);
			}
			else if("shortvideo".equals(msgType))
			{
				baseMessage=JSON.parseObject(jsonStr, VideoMessage.class);
			}
			else if("video".equals(msgType))
			{
				baseMessage=JSON.parseObject(jsonStr, VoiceMessage.class);
			}
			else if("location".equals(msgType))
			{
				baseMessage=JSON.parseObject(jsonStr, LocationMessage.class);
			}
			else if("link".equals(msgType))
			{
				baseMessage=JSON.parseObject(jsonStr, LinkMessage.class);
			}
			else if("event".equals(msgType))
			{
				if(map.get("Event")!=null)
				{
					String event=map.get("Event");
					if("subscribe".equals(event)||"unsubscribe".equals(event)||"CLICK".equals(event)||"VIEW".equals(event))
					{
						baseMessage=JSON.parseObject(jsonStr, EventMessage.class);
					}
					else if("LOCATION".equals(event))
					{
						baseMessage=JSON.parseObject(jsonStr, EventLocationMessage.class);
					}
				}
				
			}
		}
		return baseMessage;
	}
	public static void main(String[] args) {
		String text="{\"MsgId\": \"6156049365135717388\",\"FromUserName\": \"oJQtLs5hvi4Zr0iAchGtALoiI3IQ\",\"CreateTime\": \"1433316936\",\"Content\": \"你好啊\",\"ToUserName\": \"gh_192accce6ffa\",\"MsgType\": \"text\"}";
	
		Map<String, String> map = JSON.parseObject(text, new TypeReference<Map<String, String>>() {});
		
		String jsonStr=JSON.toJSONString(map);
		System.out.println(jsonStr);
		try {
			BaseMessage bas=RequestMessageManager.getBaseMessage(map);
			System.out.println(bas.getMsgId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
