package com.ifp.weixin.entity.message.req;

/**
 * 地理位置消息
 * 
 */
public class EventLocationMessage extends BaseMessage {
	private String Event;
	/**
	 * 地理位置维度
	 */
	private String Latitude;
	/**
	 * 地理位置经度
	 */
	private String Longitude;

	/**
	 * 地理位置精度
	 */
	private String Precision;

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getPrecision() {
		return Precision;
	}

	public void setPrecision(String precision) {
		Precision = precision;
	}

	

}
