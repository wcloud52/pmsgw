package com.saas.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.saas.biz.pojo.PmsgwGameDetail;
import com.saas.biz.pojo.PmsgwGameMaster;
import com.saas.biz.service.PmsgwGameDetailService;
import com.saas.biz.service.PmsgwGameMasterService;

@Component
public class SpeedpigeoncncomAsyncTask {

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

	public void crawler() {
		crawlerMaster();
		List<PmsgwGameMaster> list = pmsgwGameMasterService.selectListByWebsiteCreatetime("speed.pigeoncn.com");
		for (PmsgwGameMaster obj : list) {
			Long id = obj.getId();
			String strCrawlerCurPageid = obj.getCrawlerCurPageid();
			if (strCrawlerCurPageid == null) {
				crawlerMasterExt(id);
			}
		}
		list = pmsgwGameMasterService.selectListByWebsiteCreatetime("speed.pigeoncn.com");
		for (PmsgwGameMaster obj : list) {
			Long id = obj.getId();
			String strCrawlerCurPageid = obj.getCrawlerCurPageid();
			if (strCrawlerCurPageid == null)
				strCrawlerCurPageid = "1";
			Integer crawlerCurPageid = Integer.parseInt(strCrawlerCurPageid);
			Integer totalCount=obj.getData_num();
			if(totalCount==0)
				totalCount=10000;
			crawlerDetail(id,totalCount, crawlerCurPageid);
		}
	}
	
	public void crawler2() {
		
		PmsgwGameMaster obj = pmsgwGameMasterService.selectTopOne("【兰州鼎盛赛鸽公棚(春)】 鼎盛2018春棚第三关决赛");
		
			Long id = obj.getId();
			String strCrawlerCurPageid = obj.getCrawlerCurPageid();
			if (strCrawlerCurPageid == null)
				strCrawlerCurPageid = "1";
			Integer crawlerCurPageid = Integer.parseInt(strCrawlerCurPageid);
			Integer totalCount=obj.getData_num();
			if(totalCount==0)
				totalCount=10000;
			crawlerDetail(id,totalCount, crawlerCurPageid);
		
	}
	public void crawlerMaster() {
		String url = evn.getProperty("crawlerUrl.pigeoncn.com") + "/speedpigeoncncom/master";// http://localhost:3000/speedpigeoncncom/master
		ParameterizedTypeReference<List<PmsgwGameMaster>> typeRef = new ParameterizedTypeReference<List<PmsgwGameMaster>>() {
		};
		ResponseEntity<List<PmsgwGameMaster>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
		List<PmsgwGameMaster> list = responseEntity.getBody();
		for (PmsgwGameMaster obj : list) {
			obj.setCreate_time(new Date());
			obj.setModify_time(new Date());
			pmsgwGameMasterService.insert(obj);
		}
	}
	public PmsgwGameMaster crawlerMasterExt(Long id) {
		PmsgwGameMaster result = pmsgwGameMasterService.selectOneById(id);
        
		String url = evn.getProperty("crawlerUrl.pigeoncn.com") + "/speedpigeoncncom/master/ext?type="+result.getMain_type()+"&path=" + result.getMain_href().replace(".html", ".js").replace("T3_", "gp_bsxx_");
		ParameterizedTypeReference<PmsgwGameMaster> typeRef = new ParameterizedTypeReference<PmsgwGameMaster>() {};
		ResponseEntity<PmsgwGameMaster> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
		PmsgwGameMaster obj = responseEntity.getBody();
		obj.setId(id);
		obj.setModify_time(new Date());
	
		obj.setCrawlerCurTotal(obj.getData_num().toString());
		
		pmsgwGameMasterService.update( obj);

		return obj;
	}
	public Integer crawlerPagecount(Integer totalCount) {
		Integer num=totalCount;
		Integer page = 1;
		if(num!=null)
		{
			int count = 100;
			int size = num;
			page = size % count == 0 ? size / count : size / count + 1;
		}
		return page;
	}
	@Async
	public void crawlerDetail(Long id,Integer totalCount, Integer crawlerCurPageid) {
		Integer page = crawlerPagecount(totalCount);
		if (page != null && page > 0) {
			for (int i = crawlerCurPageid; i <= page; i++) {
				int ret=crawlerDetailList(id, i);
				if(ret<100)
				{
					page=i;
					break;
				}
			}
		}
		PmsgwGameMaster obj = new PmsgwGameMaster();
		obj.setId(id);
		obj.setCrawlerCurPageid(page.toString());
		pmsgwGameMasterService.update(obj);
	}
	
	public int crawlerDetailList(Long id, Integer curPage) {
		int ret=0;
		PmsgwGameMaster result = pmsgwGameMasterService.selectOneById(id);
		String masterText = result.getMain_text();
		String type = result.getMain_type();
		String mainhref = result.getMain_href();
		String bigfile = result.getBigfile();
		List<String> pathList = JSON.parseArray(bigfile, String.class);

		HashSet<String> h = new HashSet<String>(pathList);
		pathList.clear();
		pathList.addAll(h);
		for (String path : pathList) {
			String urlPath = path + "json_" + result.getTablename() + "_" + result.getQh() + "_" + result.getRace_id() + "_" + curPage + "h.v2.data?" + UUID.randomUUID().toString() + "&callback=?";
			
			String url = evn.getProperty("crawlerUrl.pigeoncn.com") + "/speedpigeoncncom/detail?path=" + urlPath;
			log.info(url);
			ParameterizedTypeReference<List<PmsgwGameDetail>> typeRef = new ParameterizedTypeReference<List<PmsgwGameDetail>>() {
			};
			ResponseEntity<List<PmsgwGameDetail>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
			if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
				continue;
			}
			List<PmsgwGameDetail> listBody = responseEntity.getBody();
			 
			for (PmsgwGameDetail detail : listBody) {
				if(detail.getRank()==null)
				{
					String t1=detail.getSpeed().replace("科汇自动", "").replace("科汇手持", "").replaceAll("科汇人工", "").replace("科汇机械", "").replace("科汇手动", "") ;
					float f1=Float.parseFloat(t1);
					int rank=(int) (f1*10000)*(-1);
					detail.setRank(rank);
				}
			}
			List<PmsgwGameDetail> listIn = pmsgwGameDetailService.selectListByList(id, listBody);
			Map<String, String> map = new HashMap<String, String>();
			
			for (PmsgwGameDetail detail : listIn) {
				map.put(id + "/" + detail.getRank()+ "/" + detail.getCotenum(), "1");
			}
			List<PmsgwGameDetail> list = new ArrayList<PmsgwGameDetail>();
			for (PmsgwGameDetail detail : listBody) {
				String key = id + "/" + detail.getRank()+ "/" + detail.getCotenum();
				if (!map.containsKey(key)) {
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
				sendMessageAsyncTask.autoSendResultMessage(list, type, mainhref);
			}
			
			if(listBody!=null&&listBody.size()>0)
			{
				ret=listBody.size();
            	break;
			}
		}

		return ret;
	}
}
