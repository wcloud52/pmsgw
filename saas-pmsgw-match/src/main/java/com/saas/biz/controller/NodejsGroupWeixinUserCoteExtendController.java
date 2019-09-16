package com.saas.biz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.biz.pojo.NodejsCustomerTextMessage;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.pojo.NodejsWeixinUserCote;
import com.saas.biz.pojo.NodejsWeixinUserCoteExtend;
import com.saas.biz.service.NodejsCustomerTextMessageService;
import com.saas.biz.service.NodejsWeixinUserCoteExtendService;
import com.saas.biz.service.NodejsWeixinUserCoteService;
import com.saas.biz.util.EmojiFilter;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.OpEnum;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
/**
 * 组用户发送客服消息
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/nodejsGroupWeixinUserCoteExtend")
public class NodejsGroupWeixinUserCoteExtendController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsGroupWeixinUserCoteExtendController.class);

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	protected NodejsWeixinUserCoteExtendService sv;

	@Autowired
	protected NodejsCustomerTextMessageService nodejsCustomerTextMessageService;

	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "nodejsGroupWeixinUserCoteExtend/list";
	}

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(ModelMap model) {

		return "nodejsGroupWeixinUserCoteExtend/edit";
	}

	/**
	 * 列表
	 * 
	 * @param body
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex,
			@RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort,
			@RequestParam("fuzzyQuery") String fuzzyQuery,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/"
					+ fuzzyQuery);
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		
		 SSOToken ssoToken = SSOHelper.getSSOToken(request); 
		 
         if (ssoToken != null) {
        	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);
        		
        	 nodes.add(new QueryNode("cote_id", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), nodejsSysUser.getCote_id()));
         }
		
         QueryNodes queryNode=QueryNodes.createQueryNodes(nodes, "and");
		
		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);
		queryNodes.add(queryNode);
		
		
		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsWeixinUserCoteExtend>, Object>> restult = PagingAndSortingRepository.find(query,
				new PageSpecification<NodejsWeixinUserCoteExtend>() {
					@Override
					public List<NodejsWeixinUserCoteExtend> query(Map<Object, Object> map) {
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
		List<NodejsWeixinUserCoteExtend> list = restult.getData().getList();
		for (NodejsWeixinUserCoteExtend obj : list) {
			if (obj.getNickname() != null && obj.getNickname().length() > 0)
				obj.setNickname(EmojiFilter.filterBase64Decode(obj.getNickname()));
		}
		long count = restult.getData().getTotal();
		int code = restult.getCode();
		String msg = restult.getMessage();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		map.put("msg", msg);
		map.put("count", count);
		map.put("data", list);
		return map;
	}

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> sendMessage(@RequestBody Map<String, Object> body,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WapController.class + "/sendMessage->" + JSON.toJSONString(body));
         /*SSOToken ssoToken = SSOHelper.getSSOToken(request); 
		 
         if (ssoToken != null) {
        	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);
        		
         }*/
         
		List<NodejsWeixinUserCoteExtend> list = JSON.parseArray(body.get("ext").toString(), NodejsWeixinUserCoteExtend.class);
		Map<String, String> base = (Map<String, String>) body.get("base");
		for (NodejsWeixinUserCoteExtend user : list) {
			String first = base.get("first");
			String keyword1 = base.get("keyword1");
			String keyword2 = base.get("keyword2");
			String keyword3 = base.get("keyword3");
			String remark = base.get("remark");

			// 实例化模板对象
			String templateId = "";
			String openid = user.getOpenid();
			String master_date = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
			WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
			// 设置模板ID
			wxMpTemplateMessage.setTemplateId("5tktowZNI7ZrjRCehl5hAB8sXeoT79TFTP3Ttqa7pnI");
			// 设置发送给哪个用户
			wxMpTemplateMessage.setToUser(openid);
			// wxMpTemplateMessage.setUrl(url);

			List<WxMpTemplateData> listWx = Arrays.asList(new WxMpTemplateData("first", first, "#000000"),
					new WxMpTemplateData("keyword1", keyword1, "#000000"),
					new WxMpTemplateData("keyword2", keyword2, "#000000"),
					new WxMpTemplateData("keyword3", keyword3, "#000000"),
					new WxMpTemplateData("remark",remark, "#000000"));
			// 放进模板对象。准备发送
			wxMpTemplateMessage.setData(listWx);
            String text="";
			try {
				wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
				text=JSON.toJSONString(wxMpTemplateMessage);
			} catch (WxErrorException e) {
				text=e.getMessage();
				log.info(e.getMessage());
			}

			NodejsCustomerTextMessage record = new NodejsCustomerTextMessage();
			record.setMessage_id(UUID.randomUUID().toString());
			record.setMessage_receiverId(user.getOpenid());
			record.setMessage_receiverName("");
			
			record.setMessage_senderId(user.getCote_id());
			record.setMessage_senderName(user.getCote_name());
			
			record.setMessage_title(keyword1);
			record.setMessage_text(text);
			record.setMessage_type("text");
			record.setMessage_create_time(new Date());
			record.setMessage_modify_time(new Date());
			nodejsCustomerTextMessageService.insert(record);

		}

		return BaseResponse.ToJsonResult(1);
	}
}