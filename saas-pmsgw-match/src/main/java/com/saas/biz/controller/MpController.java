package com.saas.biz.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.saas.biz.pojo.NodejsCrawlerCoteExtend;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;
import com.saas.biz.pojo.NodejsNews;
import com.saas.biz.pojo.NodejsPigeonCollection;
import com.saas.biz.pojo.NodejsWeixinUserCoteExtend;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.NodejsCrawlerCoteExtendService;
import com.saas.biz.service.NodejsCrawlerService;
import com.saas.biz.service.NodejsNewsService;
import com.saas.biz.service.NodejsPigeonCollectionService;
import com.saas.biz.service.NodejsWeixinUserCoteExtendService;
import com.saas.biz.service.WeixinUserService;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.OpEnum;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;
import com.saas.common.SignEnum;

/**
 * 小程序
 * 
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/mp")
public class MpController {

	private static final Logger log = LoggerFactory.getLogger(MpController.class);

	@Autowired
	protected NodejsNewsService sv;

	@Autowired
	private NodejsCrawlerService nodejsCrawlerService;

	@Autowired
	protected NodejsCrawlerCoteExtendService nodejsCrawlerCoteExtService;

	@Autowired
	protected NodejsWeixinUserCoteExtendService nodejsWeixinUserCoteExtendService;

	@Autowired
	private WeixinUserService weixinUserService;
	@Autowired
	protected NodejsPigeonCollectionService nodejsPigeonCollectionService;

	@RequestMapping(value = "/focuNews", method = RequestMethod.GET)
	@ResponseBody
	public Map<Object, Object> selectListNewsc(@RequestParam("pageId") Integer pageIndex) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinMessageController.class + "/list->" + pageIndex);

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("news_type", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), "News"));
		List<QueryNodes> queryNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(10);
		query.setSort("");
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsNews>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsNews>() {
			@Override
			public List<NodejsNews> query(Map<Object, Object> map) {
				return sv.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return sv.selectCountByDynamic(map);
			}
		});
		List<NodejsNews> list = restult.getData().getList();

		List<Map<Object, Object>> listMap = new ArrayList<>();
		for (NodejsNews news : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("imgHref", news.getNews_imgHref());
			map.put("title", news.getNews_title());
			map.put("time", new DateTime(news.getNews_sendTime()).toString("yyyy-MM-dd HH:mm:ss"));
			map.put("desc", news.getNews_desc());
			map.put("detailHrefId", news.getNews_id());

			listMap.add(map);
		}

		Map<Object, Object> retmap = new HashMap<Object, Object>();
		retmap.put("htmlBody", listMap);

		return retmap;
	}

	@RequestMapping(value = "/announce", method = RequestMethod.GET)
	@ResponseBody
	public Map<Object, Object> announce(@RequestParam("pageId") Integer pageIndex) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinMessageController.class + "/list->" + pageIndex);

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("news_type", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), "Announce"));
		List<QueryNodes> queryNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(10);
		query.setSort("");
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsNews>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsNews>() {
			@Override
			public List<NodejsNews> query(Map<Object, Object> map) {
				return sv.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return sv.selectCountByDynamic(map);
			}
		});
		List<NodejsNews> list = restult.getData().getList();

		List<Map<Object, Object>> listMap = new ArrayList<>();
		for (NodejsNews news : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("imgHref", news.getNews_imgHref());
			map.put("title", news.getNews_title());
			map.put("time", new DateTime(news.getNews_sendTime()).toString("yyyy-MM-dd HH:mm:ss"));
			map.put("desc", news.getNews_desc());
			map.put("detailHrefId", news.getNews_id());

			listMap.add(map);
		}

		Map<Object, Object> retmap = new HashMap<Object, Object>();
		retmap.put("htmlBody", listMap);

		return retmap;
	}

	/**
	 * 规章
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	@ResponseBody
	public Map<Object, Object> notice() throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinMessageController.class + "/list->");

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("news_type", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), "regulation"));
		List<QueryNodes> queryNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(1);
		query.setPageSize(100);
		query.setSort("news_sendTime desc ");
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsNews>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsNews>() {
			@Override
			public List<NodejsNews> query(Map<Object, Object> map) {
				return sv.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return sv.selectCountByDynamic(map);
			}
		});
		List<NodejsNews> list = restult.getData().getList();

		List<Map<Object, Object>> listMap = new ArrayList<>();
		for (NodejsNews news : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("imgHref", news.getNews_imgHref());
			map.put("title", news.getNews_title());
			map.put("time", new DateTime(news.getNews_sendTime()).toString("yyyy-MM-dd HH:mm:ss"));
			map.put("desc", news.getNews_desc());
			map.put("detailHrefId", news.getNews_id());

			listMap.add(map);
		}

		Map<Object, Object> retmap = new HashMap<Object, Object>();
		retmap.put("htmlBody", listMap);

		return retmap;
	}

	/**
	 * 轮播图
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/uestcSpecial", method = RequestMethod.GET)
	@ResponseBody
	public Map<Object, Object> uestcSpecial() throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinMessageController.class + "/list->");

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("news_type", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), "Special"));
		List<QueryNodes> queryNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(1);
		query.setPageSize(10);
		query.setSort("");
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsNews>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsNews>() {
			@Override
			public List<NodejsNews> query(Map<Object, Object> map) {
				return sv.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return sv.selectCountByDynamic(map);
			}
		});
		List<NodejsNews> list = restult.getData().getList();

		List<Map<Object, Object>> listMap = new ArrayList<>();
		for (NodejsNews news : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("imgHref", news.getNews_imgHref());
			map.put("title", news.getNews_title());
			map.put("time", new DateTime(news.getNews_sendTime()).toString("yyyy-MM-dd HH:mm:ss"));
			map.put("desc", news.getNews_desc());
			map.put("detailHrefId", news.getNews_id());

			listMap.add(map);
		}

		Map<Object, Object> retmap = new HashMap<Object, Object>();
		retmap.put("htmlBody", listMap);

		return retmap;
	}

	@RequestMapping(value = "/uestcNoticeDetail", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String uestcNoticeDetail(@RequestParam("pageId") String pageIndex) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinMessageController.class + "/list->" + pageIndex);
		NodejsNews nodejsNews = sv.selectOneById(pageIndex);
		String text = nodejsNews.getNews_text();
		return text;
	}

	/**
	 * slider轮播图
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/slider", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<Object, Object>> slider() throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinMessageController.class + "/slider->");

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("news_type", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), "slider"));
		List<QueryNodes> queryNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(1);
		query.setPageSize(10);
		query.setSort("news_sendTime desc ");
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsNews>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsNews>() {
			@Override
			public List<NodejsNews> query(Map<Object, Object> map) {
				return sv.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return sv.selectCountByDynamic(map);
			}
		});
		List<NodejsNews> list = restult.getData().getList();

		List<Map<Object, Object>> listMap = new ArrayList<>();
		for (NodejsNews news : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("image", news.getNews_imgHref());
			map.put("title", news.getNews_title());
			map.put("time", new DateTime(news.getNews_sendTime()).toString("yyyy-MM-dd HH:mm:ss"));
			map.put("desc", news.getNews_desc());
			map.put("id", news.getNews_id());

			listMap.add(map);
		}

		return listMap;
	}

	/**
	 * info资讯
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<Object, Object>> info(@RequestParam("pageIndex") Integer pageIndex) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinMessageController.class + "/info->");

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("news_type", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), "news"));
		// nodes.add(new QueryNode("news_type", OpEnum.EQUALS.getName(),
		// PrependEnum.AND.getName(), news_type));
		// nodes.add(new QueryNode("DATE_FORMAT(news_sendTime, '%Y-%m-%d')",
		// OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), news_sendTime));

		List<QueryNodes> queryNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(20);
		query.setSort("news_sendTime desc ");
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsNews>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsNews>() {
			@Override
			public List<NodejsNews> query(Map<Object, Object> map) {
				return sv.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return sv.selectCountByDynamic(map);
			}
		});
		List<NodejsNews> list = restult.getData().getList();

		List<Map<Object, Object>> listMap = new ArrayList<>();
		for (NodejsNews news : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("image", news.getNews_imgHref());
			map.put("title", news.getNews_title());
			map.put("time", new DateTime(news.getNews_sendTime()).toString("yyyy-MM-dd HH:mm:ss"));
			map.put("desc", news.getNews_desc());
			map.put("cotename", news.getCote_name());
			map.put("id", news.getNews_id());

			listMap.add(map);
		}

		return listMap;
	}

	/**
	 * slider轮播图明细
	 * 
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sliderDetail", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String sliderDetail(@RequestParam("id") String id) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinMessageController.class + "/sliderDetail->" + id);
		NodejsNews nodejsNews = sv.selectOneById(id);
		String text = nodejsNews.getNews_text();
		return text;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/racelist")
	@ResponseBody
	public List<NodejsCrawlerMasterGame> racelist(ModelMap model, HttpServletRequest req) {
		List<NodejsCrawlerMasterGame> item = nodejsCrawlerService.selectTop50NodejsCrawlerMasterGameList("pmsgw_weixin");
		return item;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/racedetaillist")
	@ResponseBody
	public Map<String, Object> racedetaillist(@RequestParam("pigowner") String pigowner, @RequestParam("masterId") String masterId, @RequestParam("pageIndex") Integer pageIndex, HttpServletRequest req) {
		NodejsCrawlerMasterGame master = nodejsCrawlerService.selectNodejsCrawlerMasterGameById("pmsgw_weixin", masterId);
		List<NodejsCrawlerDetailGame> item = nodejsCrawlerService.selectNodejsCrawlerDetailGameListByMasterId("pmsgw_weixin", pigowner, masterId, pageIndex, 50);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("master", master);
		map.put("detail", item);
		return map;
	}

	/**
	 * 公棚列表
	 * 
	 * @param pigowner
	 * @param masterId
	 * @param pageIndex
	 * @param req
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/loft/list")
	@ResponseBody
	public List<NodejsCrawlerCoteExtend> loftlist() {
		List<NodejsCrawlerCoteExtend> list = nodejsCrawlerCoteExtService.selectByCotestate("1");
		return list;
	}

	/**
	 * 公棚用户设置对应关系
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/loft/usersave", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> usercotesave(NodejsWeixinUserCoteExtend body) {

		NodejsWeixinUserCoteExtend record = body;
		NodejsCrawlerCoteExtend nodejsCrawlerCoteExtend = nodejsCrawlerCoteExtService.selectByCoteId(record.getCote_id());
		if (nodejsCrawlerCoteExtend != null) {
			record.setCote_name(nodejsCrawlerCoteExtend.getCote_name());
			record.setCote_short_name(nodejsCrawlerCoteExtend.getCote_short_name());
			record.setCote_state(nodejsCrawlerCoteExtend.getCote_state());
			record.setCote_web_url(nodejsCrawlerCoteExtend.getCote_web_url());
			record.setCote_website(nodejsCrawlerCoteExtend.getCote_website());
			record.setSort_number(nodejsCrawlerCoteExtend.getSort_number());
		}
		nodejsWeixinUserCoteExtendService.deleteByCoteId(record.getCote_id(), record.getOpenid());
		nodejsWeixinUserCoteExtendService.insert(record);

		return BaseResponse.ToJsonResult(1);
	}

	/**
	 * 我的公棚列表
	 * 
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/loft/mylist")
	@ResponseBody
	public List<NodejsWeixinUserCoteExtend> myloftlist(@RequestParam(name = "openId") String openId) throws Exception {
		List<NodejsWeixinUserCoteExtend> list = nodejsWeixinUserCoteExtendService.selectByOpenid(openId);
		return list;
	}
    /**
         *    我的收鸽集鸽数据列表
     * @param cote_id
     * @param openid
     * @param type
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws UnsupportedEncodingException
     */
	@RequestMapping(value = "/loft/mypigeoncollection", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<JsonResult<List<NodejsPigeonCollection>, Object>> mypigeoncollectionData(@RequestParam("cote_id") String cote_id, @RequestParam("openid") String openid, @RequestParam("type") String type, @RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize) throws UnsupportedEncodingException {

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("typeId", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), type, SignEnum.YES.getName()));
		nodes.add(new QueryNode("cote_id", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), cote_id, SignEnum.YES.getName()));

		WeixinUser weixinUser = weixinUserService.selectOneById(openid);
		if (weixinUser != null) {
			nodes.add(new QueryNode("pigowner", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), weixinUser.getBind_name(), SignEnum.YES.getName()));
		}

		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort("create_time desc");
		query.setFuzzyQuery(listNodes);

		BaseResponse<JsonResult<List<NodejsPigeonCollection>, Object>> result = PagingAndSortingRepository.find(query, new PageSpecification<NodejsPigeonCollection>() {
			@Override
			public List<NodejsPigeonCollection> query(Map<Object, Object> map) {
				return nodejsPigeonCollectionService.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return nodejsPigeonCollectionService.selectCountByDynamic(map);
			}
		});
		return result;
	}
}