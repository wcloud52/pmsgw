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
public class YunfeichinacomAsyncTask {

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
		//crawlerMaster2();
		List<PmsgwGameMaster> list = pmsgwGameMasterService.selectListByWebsiteCreatetime("yunfeichina.com");
		for (PmsgwGameMaster obj : list) {
			Long id = obj.getId();
			crawlerDetail(id);
		}
	}
	public void crawlerMaster() {
		String url1 = evn.getProperty("crawlerUrl.pigeoncn.com") + "/yunfeichinacom/master";// http://localhost:3000/yunfeichinacom/master
		ParameterizedTypeReference<List<String>> typeRef1 = new ParameterizedTypeReference<List<String>>() {
		};
		ResponseEntity<List<String>> responseEntity1 = restTemplate.exchange(url1, HttpMethod.GET, null, typeRef1);
		List<String> list = responseEntity1.getBody();
		
		for (String path:list) {
			
			String url2 = evn.getProperty("crawlerUrl.pigeoncn.com") + "/yunfeichinacom/master/ext?path=" + path;

			ParameterizedTypeReference<List<PmsgwGameMaster>> typeRef2 = new ParameterizedTypeReference<List<PmsgwGameMaster>>() {
			};
			ResponseEntity<List<PmsgwGameMaster>> responseEntity2 = restTemplate.exchange(url2, HttpMethod.GET, null, typeRef2);
			List<PmsgwGameMaster> list2 = responseEntity2.getBody();
			for (PmsgwGameMaster obj : list2) {
				obj.setCreate_time(new Date());
				obj.setModify_time(new Date());
				obj.setCrawlerCurUrl(obj.getMain_href());
				pmsgwGameMasterService.insert(obj);
			}
		}
	}
	public void crawlerMaster2() {
		String url1 = evn.getProperty("crawlerUrl.pigeoncn.com") + "/yunfeichinacom/master";// http://localhost:3000/yunfeichinacom/master
		ParameterizedTypeReference<List<String>> typeRef1 = new ParameterizedTypeReference<List<String>>() {
		};
		ResponseEntity<List<String>> responseEntity1 = restTemplate.exchange(url1, HttpMethod.GET, null, typeRef1);
		List<String> list = responseEntity1.getBody();
		
		for (String path:list) {
			
			String url2 = evn.getProperty("crawlerUrl.pigeoncn.com") + "/yunfeichinacom/master2?path=" + path;

			ParameterizedTypeReference<List<String>> typeRef2 = new ParameterizedTypeReference<List<String>>() {
			};
			ResponseEntity<List<String>> responseEntity2 = restTemplate.exchange(url2, HttpMethod.GET, null, typeRef2);
			List<String> list2 = responseEntity2.getBody();
			
			for (String path2:list2) {
				
				String url22 = evn.getProperty("crawlerUrl.pigeoncn.com") + "/yunfeichinacom/master/ext?path=" + path2;

				ParameterizedTypeReference<List<PmsgwGameMaster>> typeRef22 = new ParameterizedTypeReference<List<PmsgwGameMaster>>() {
				};
				ResponseEntity<List<PmsgwGameMaster>> responseEntity22 = restTemplate.exchange(url22, HttpMethod.GET, null, typeRef22);
				List<PmsgwGameMaster> list22 = responseEntity22.getBody();
				for (PmsgwGameMaster obj : list22) {
					obj.setCreate_time(new Date());
					obj.setModify_time(new Date());
					obj.setCrawlerCurUrl(obj.getMain_href());
					pmsgwGameMasterService.insert(obj);
				}
			}
			
		}
	}
	public String crawlerDetail(Long id) {
		PmsgwGameMaster result = pmsgwGameMasterService.selectOneById(id);
		crawlerDetail(result);
		
		String urlPath = result.getCrawlerCurUrl();
		String url = evn.getProperty("crawlerUrl.pigeoncn.com") + "/yunfeichinacom/pagecount?path=" + urlPath;
		ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
		};
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);

		String crawlerCurUrl = responseEntity.getBody();
		if(!"http://www.yunfeichina.com/undefined".equals(crawlerCurUrl))
		{
			PmsgwGameMaster pmsgwGameMaster=new PmsgwGameMaster();
			pmsgwGameMaster.setId(id);
			pmsgwGameMaster.setCrawlerCurUrl(crawlerCurUrl);
			pmsgwGameMasterService.update(pmsgwGameMaster);
			crawlerDetail(id);
		}
		return crawlerCurUrl;
	}
	//@Async
	public void crawlerDetail(PmsgwGameMaster result) {
		Long id=result.getId();
		String masterText=result.getMain_text();
		
		String urlPath = result.getCrawlerCurUrl();
		String type = result.getMain_type();
		String url = evn.getProperty("crawlerUrl.pigeoncn.com") + "/yunfeichinacom/detail?path=" + urlPath;
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
				pmsgwGameDetailService.insertByList(list);
				sendMessageAsyncTask.autoSendResultMessage(list, type, urlPath);
		}
	}
}
