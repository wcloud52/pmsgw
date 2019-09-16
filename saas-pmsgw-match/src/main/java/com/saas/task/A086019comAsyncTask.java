package com.saas.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.saas.biz.pojo.PmsgwGameDetail;
import com.saas.biz.pojo.PmsgwGameMaster;
import com.saas.biz.service.PmsgwGameDetailService;
import com.saas.biz.service.PmsgwGameMasterService;

@Component
public class A086019comAsyncTask {

	@Autowired
	private PmsgwGameMasterService pmsgwGameMasterService;

	@Autowired
	private PmsgwGameDetailService pmsgwGameDetailService;

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Environment evn;

	@Autowired
	private SendMessageAsyncTask sendMessageAsyncTask;
	
	public void crawler() {
		crawlerMaster();
		List<PmsgwGameMaster> list = pmsgwGameMasterService.selectListByWebsiteCreatetime("086019.com");
		for (PmsgwGameMaster obj : list) {
			Long id = obj.getId();
			crawlerDetail(id);
		}
	}
	public void crawlerMaster() {
		String url1 = evn.getProperty("crawlerUrl.pigeoncn.com") + "/086019com/master";// http://localhost:3000/086019com/master
		ParameterizedTypeReference<Map<String, String>> typeRef1 = new ParameterizedTypeReference<Map<String, String>>() {
		};
		ResponseEntity<Map<String, String>> responseEntity1 = restTemplate.exchange(url1, HttpMethod.GET, null, typeRef1);
		Map<String, String> map = responseEntity1.getBody();
		String url = map.get("href");
		int page = 1;//Integer.valueOf(map.get("page"));
		for (int i = 0; i < page; i++) {
			String urlPath = url + i + ".html";
			String url2 = evn.getProperty("crawlerUrl.pigeoncn.com") + "/086019com/master/page?path=" + urlPath;

			ParameterizedTypeReference<List<PmsgwGameMaster>> typeRef2 = new ParameterizedTypeReference<List<PmsgwGameMaster>>() {
			};
			ResponseEntity<List<PmsgwGameMaster>> responseEntity2 = restTemplate.exchange(url2, HttpMethod.GET, null, typeRef2);
			List<PmsgwGameMaster> list2 = responseEntity2.getBody();
			for (PmsgwGameMaster obj : list2) {
				obj.setCreate_time(new Date());
				obj.setModify_time(new Date());
				pmsgwGameMasterService.insert(obj);
			}
		}
	}

	@Async
	public void crawlerDetail(Long id) {
		PmsgwGameMaster result = pmsgwGameMasterService.selectOneById(id);
		String masterText=result.getMain_text();
		
		String urlPath = result.getMain_number();
		String mainhref = result.getMain_href();
		String type = result.getMain_type();
		String url = evn.getProperty("crawlerUrl.pigeoncn.com") + "/086019com/detail?path=" + urlPath;
		ParameterizedTypeReference<List<PmsgwGameDetail>> typeRef = new ParameterizedTypeReference<List<PmsgwGameDetail>>() {
		};
		ResponseEntity<List<PmsgwGameDetail>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);

		List<PmsgwGameDetail> listBody = responseEntity.getBody();
		List<PmsgwGameDetail> listIn=pmsgwGameDetailService.selectListByList(id,listBody);
		Map<String,String> map=new HashMap<String,String>();
		for(PmsgwGameDetail detail:listIn)
		{
			map.put(id+"/"+detail.getRank(), "1");
		}
		List<PmsgwGameDetail> list=new ArrayList<PmsgwGameDetail>();
		for(PmsgwGameDetail detail:listBody)
		{
			String key=id+"/"+detail.getRank();
			if(!map.containsKey(key))
			{
				list.add(detail);
			}
		}
		if (list != null && list.size() > 0) {
		
			for (PmsgwGameDetail detail : list) {
				detail.setId(UUID.randomUUID().getLeastSignificantBits());
				detail.setMasterId(id);
				detail.setMasterText(masterText);
				detail.setFlag("0");
				detail.setCreate_time(new Date());
				detail.setModify_time(new Date());

			}
			int count = 200;
			int size = list.size();
			int page = size % count == 0 ? size / count : size / count + 1;

			for (int ii = 0; ii < page; ii++) {
				int fromIndex = count *ii;
				int toIndex = fromIndex + count;
				if (toIndex >= size) {
					toIndex = size;
				}
				pmsgwGameDetailService.insertByList(list.subList(fromIndex, toIndex));
				sendMessageAsyncTask.autoSendResultMessage(list.subList(fromIndex, toIndex), type, mainhref);
			}
		}
	}
}
