package com.saas.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class Aj52zxcomAsyncTask {

	private static final Logger log = LoggerFactory.getLogger(Aj52zxcomAsyncTask.class);

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

	public void crawler(){
		crawlerMaster();
		List<PmsgwGameMaster> list = pmsgwGameMasterService.selectListByWebsiteCreatetime("aj52zx.com");
		for (PmsgwGameMaster obj : list) {
			Long id = obj.getId();
			String strCrawlerCurPageid = obj.getCrawlerCurPageid();
			if (strCrawlerCurPageid == null)
				strCrawlerCurPageid = "1";
			Integer crawlerCurPageid = Integer.parseInt(strCrawlerCurPageid);

			crawlerDetail(id, crawlerCurPageid);
		}
	}
	public Integer crawlerPagecount(Long id) {
		PmsgwGameMaster result = pmsgwGameMasterService.selectOneById(id);

		String urlPath = result.getMain_href();
		String url = evn.getProperty("crawlerUrl.pigeoncn.com") + "/aj52zxcom/pagecount?path=" + urlPath;

		ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
		};
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);

		Integer ret = 1;
		if (responseEntity.getBody() != null && responseEntity.getBody().length() > 0)
			ret = Integer.parseInt(responseEntity.getBody());

		return ret;
	}

	public void crawlerMaster() {
		for (int ii = 1; ii <= 5; ii++) {
			String url1 = evn.getProperty("crawlerUrl.pigeoncn.com") + "/aj52zxcom/master/club?page="+ii;// http://localhost:3000/aj52zxcom/master/club
			ParameterizedTypeReference<List<PmsgwGameMaster>> typeRef1 = new ParameterizedTypeReference<List<PmsgwGameMaster>>() {
			};
			ResponseEntity<List<PmsgwGameMaster>> responseEntity1 = restTemplate.exchange(url1, HttpMethod.GET, null, typeRef1);
			List<PmsgwGameMaster> list1 = responseEntity1.getBody();
			for (PmsgwGameMaster obj : list1) {
				obj.setCreate_time(new Date());
				obj.setModify_time(new Date());
				pmsgwGameMasterService.insert(obj);
			}
		}
		String url2 = evn.getProperty("crawlerUrl.pigeoncn.com") + "/aj52zxcom/master/loft";// http://localhost:3000/aj52zxcom/master/loft
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

	@Async
	public void crawlerDetail(Long id, Integer crawlerCurPageid) {
		Integer page = crawlerPagecount(id);
		if (page != null && page > 0) {
			for (int i = crawlerCurPageid; i <= page; i++) {
				crawlerDetailList(id, i);
			}
		}
		PmsgwGameMaster obj = new PmsgwGameMaster();
		obj.setId(id);
		obj.setCrawlerCurPageid(page.toString());
		pmsgwGameMasterService.update(obj);
	}

	public String crawlerDetailList(Long id, Integer page) {
		PmsgwGameMaster result = pmsgwGameMasterService.selectOneById(id);
		String masterText = result.getMain_text();
		String mainhref = result.getMain_href();
		String ret = "";
		String urlPath = result.getMain_href();
		String type = result.getMain_type();

		String url = evn.getProperty("crawlerUrl.pigeoncn.com") + "/aj52zxcom/detail/" + type + "?path=" + urlPath + "&page=" + page;
		log.info("Aj52zxcomAsyncTask->" + url);
		ParameterizedTypeReference<List<PmsgwGameDetail>> typeRef = new ParameterizedTypeReference<List<PmsgwGameDetail>>() {
		};
		ResponseEntity<List<PmsgwGameDetail>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);

		List<PmsgwGameDetail> listBody = responseEntity.getBody();
		List<PmsgwGameDetail> listIn = pmsgwGameDetailService.selectListByList(id, listBody);
		Map<String, String> map = new HashMap<String, String>();
		for (PmsgwGameDetail detail : listIn) {
			map.put(id + "/" + detail.getRank(), "1");
		}
		List<PmsgwGameDetail> list = new ArrayList<PmsgwGameDetail>();
		for (PmsgwGameDetail detail : listBody) {
			String key = id + "/" + detail.getRank();
			if (!map.containsKey(key)) {
				list.add(detail);
			}
		}
		if (list != null && list.size() > 0) {
			List<PmsgwGameDetail> listInsert = new ArrayList<PmsgwGameDetail>();
			for (PmsgwGameDetail detail : list) {
				detail.setId(UUID.randomUUID().getLeastSignificantBits());
				detail.setMasterId(id);
				detail.setMasterText(masterText);
				detail.setFlag("0");

				detail.setCreate_time(new Date());
				detail.setModify_time(new Date());
				listInsert.add(detail);
			}
			pmsgwGameDetailService.insertByList(listInsert);

			sendMessageAsyncTask.autoSendResultMessage(listInsert, type, mainhref);
		}

		return ret;
	}

}
