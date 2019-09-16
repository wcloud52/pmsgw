package com.saas.biz.service;

import java.util.Date;
import java.util.List;

import com.saas.biz.pojo.NodejsCrawlerCote;

public interface NodejsCrawlerCoteService extends BaseService<NodejsCrawlerCote,String> {

     public List<NodejsCrawlerCote> selectListBy(
	   String cote_id,		
	   String cote_name,		
	   String cote_website,		
	   String cote_state,		
	   Date create_time,		
	   Date modify_time		
     );
}
