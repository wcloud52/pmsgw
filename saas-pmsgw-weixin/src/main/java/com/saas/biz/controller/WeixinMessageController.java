package com.saas.biz.controller;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.github.qcloudsms.httpclient.HTTPException;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.task.SendMessageAsyncTask;
import com.saas.task.SendMobileMessageAsyncTask;

@Controller
@RequestMapping("/weixinMessage")
public class WeixinMessageController {

	private static final Logger log = LoggerFactory.getLogger(WeixinMessageController.class);

	@Autowired
	private SendMessageAsyncTask sendMessageAsyncTask;
	
	@Autowired
	private SendMobileMessageAsyncTask sendMobileMessageAsyncTask;
	
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public String send(@RequestBody List<NodejsCrawlerDetailGame> nodejsCrawlerDetailGameList) throws JSONException, HTTPException, IOException
	{
		if (log.isDebugEnabled())
			log.debug(WeixinMessageController.class + "/send->" +nodejsCrawlerDetailGameList.size());

		sendMessageAsyncTask.sendResultMessage(nodejsCrawlerDetailGameList);
		//sendMobileMessageAsyncTask.sendResultMessage(nodejsCrawlerDetailGameList);
		return "ok";
	}
}