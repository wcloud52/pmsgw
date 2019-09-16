package com.saas.biz.service;

import java.util.Date;
import java.util.List;

import com.saas.biz.pojo.NodejsCustomerTextMessage;

public interface NodejsCustomerTextMessageService extends BaseService<NodejsCustomerTextMessage,String> {

     public List<NodejsCustomerTextMessage> selectListBy(
	   String message_id,		
	   String message_senderId,		
	   String message_senderName,		
	   String message_receiverId,		
	   String message_receiverName,		
	   Date message_sendTime,		
	   String message_type,		
	   String message_title,		
	   String message_text,		
	   Integer message_status,		
	   Date message_create_time,		
	   Date message_modify_time		
     );
}
