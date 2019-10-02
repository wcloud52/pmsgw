package com.saas.biz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.biz.pojo.*;
import com.saas.biz.service.*;
import com.saas.biz.util.EmojiFilter;
import com.saas.biz.util.SnGenerator;
import com.saas.common.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

/**
 * 移动端相关操作
 * 
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/wap")
public class WapMatchController {
	private static final Logger loger = LoggerFactory.getLogger(WapMatchController.class);

	@Autowired
	private WxMpService wxMpService;

    @Autowired
    private NodejsCustomerMessageService nodejsCustomerMessageService;
    @Autowired
    private NodejsCrawlerService nodejsCrawlerService;
    @Autowired
    private WeixinUserService weixinUserService;
    @Autowired
    protected NodejsPigeonCollectionService nodejsPigeonCollectionService;
    @Autowired
    protected NodejsMatchRegistService nodejsMatchRegistService;
    @Autowired
    protected NodejsMatchService nodejsMatchService;
    @Autowired
    protected NodejsCrawlerCoteExtendService nodejsCrawlerCoteExtendService;
    @Autowired
    protected NodejsMatchPigeonCollectionService nodejsMatchPigeonCollectionService;
    @Autowired
    protected NodejsMobileUserService nodejsMobileUserService;

    @RequestMapping(value = "page/resultOne",method = RequestMethod.GET)
    public String resultOne(Model model,@RequestParam("match_id") String match_id, @RequestParam("pigeon_code") String pigeon_code,@RequestParam("pigowner") String pigowner){
        //根据id获取比赛
        NodejsMatch nodejsMatch = nodejsMatchService.selectOneById(match_id);
        Map queryMap = new HashedMap();
        queryMap.put("match_id", match_id);
        queryMap.put("cote_id",nodejsMatch.getCote_id());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        queryMap.put("start_time",sdf.format(nodejsMatch.getStart_time()));
        queryMap.put("database","pmsgw_weixin");
        List<NodejsMatchRegist> detailGameList = nodejsMatchRegistService.selectCrawlerDetailGame(queryMap);
        JSONArray rules = JSONObject.parseArray(nodejsMatch.getRule());
        int reward=0;
        List<Map<String,Object>> result=new ArrayList<>();
        if (detailGameList.size()>0){
            List<NodejsMatchRegist> collect = detailGameList.stream().filter(item -> item.getPigeon_code().equals(pigeon_code)).collect(Collectors.toList());
            String regist = collect.get(0).getRegist();
            JSONObject registJson = JSONObject.parseObject(regist);
            for (String key:registJson.keySet() ) {
                List<NodejsMatchRegist> filterList = detailGameList.stream().filter(item -> item.getRegist().contains(key)).collect(Collectors.toList());
                filterList.stream().forEach(item -> {
                    if (item.getRank() <= 0) {
                        item.setRank(999999999);
                    }
                });
                filterList = filterList.stream().sorted((b1, b2) -> b1.getRank() - b2.getRank()).collect(Collectors.toList());
                String[] split = key.split("-");
                for (int i = 0; i < rules.size(); i++) {
                    JSONObject rule = rules.getJSONObject(i).getJSONObject("rule");
                    if (rule.getString("code").equals(split[1])){
                        result.add(nodejsMatchRegistService.echoOneResult(rule,key,filterList,pigeon_code));
                    }
                }
            }
            result.sort((a,b)->(a.get("name")+"").compareTo(b.get("name")+""));
            double sum = result.stream().collect(Collectors.summingDouble(item -> (Float) item.get("reward")));
            Map all=new HashedMap();
            all.put("name","合计");
            all.put("reward",(int)sum);
            result.add(all);
            model.addAttribute("result",result);
            model.addAttribute("pigeon_code",pigeon_code);
            model.addAttribute("pigowner",pigowner);
        }
        return "wap/resultOne";
    }

    @RequestMapping(value = "page/registList",method = RequestMethod.GET)
    public String registListPage(String match_id,Model model){
        if (loger.isDebugEnabled())
            loger.debug(NodejsMatchRegistController.class + "/wap/registList->");
        Map param=new HashMap();
        param.put("match_id",match_id);
        NodejsMatch match = nodejsMatchService.selectOneById(match_id);
        param.clear();
        param.put("match_id",match_id);
        Map queryMap=new HashMap();
        queryMap.put("queryMap",param);
        queryMap.put("sort","member_name desc");
        List<NodejsMatchRegist> list=nodejsMatchRegistService.selectListByDynamic(queryMap);
        Map<String, Integer> desc=new HashedMap();
        List<Map> mapList = new ArrayList<Map>();
        for (NodejsMatchRegist regist :list ) {
            try {
                Map describe = BeanUtils.describe(regist);
                String registJson = regist.getRegist();
                Map<String,String> json = JSONObject.parseObject(registJson, Map.class);
                describe.putAll(json);
                json.entrySet().forEach(item->{
                    if (desc.get(item.getKey())==null){
                        desc.put(item.getKey(),1);
                    }else {
                        desc.put(item.getKey(),desc.get(item.getKey())+1);
                    }
                });
                mapList.add(describe);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Map<Object, Object> descObj = new HashMap<Object, Object>();
        descObj.putAll(desc);
        descObj.put("pigeon_code","总羽数");
        List<String> ruleList=new ArrayList<>();
        Map titleList=new HashedMap();
        String match_rule = match.getRule();
        JSONArray ruleArray = JSONObject.parseArray(match_rule);
        ruleList.add("member_code");
        ruleList.add("member_name");
        ruleList.add("pigeon_code");
        titleList.put("member_code","会员编号");
        titleList.put("member_name","鸽主姓名");
        titleList.put("pigeon_code","足环号");
        String tr0="<tr><td colspan='4'>总羽数</td>";
        String tr1="<tr><td rowspan='2'>序号</td><td rowspan='2'>会员编号</td><td rowspan='2'>鸽主姓名</td><td rowspan='2'>足环号</td>";
        String tr2="<tr>";
        int width=330;
        for (int i = 0; i < ruleArray.size(); i++) {
            JSONObject rule = ruleArray.getJSONObject(i).getJSONObject("rule");
            JSONArray grade_money = rule.getJSONArray("grade_money");
            String code=rule.get("code")+"";
            String name=rule.get("name")+"";
            tr1+="<td class='color"+code+"' colspan='"+grade_money.size()+"'>"+name+"</td>";
            for (int j = 0; j < grade_money.size(); j++) {
                width+=40;
                ruleList.add("field-"+code+"-"+grade_money.getString(j));
                titleList.put("field-"+code+"-"+grade_money.getString(j),name+"    "+grade_money.getString(j)+"组");
                tr0+="<td class='color"+code+"'>"+(desc.get("field-"+code+"-"+grade_money.getString(j))==null?0:desc.get("field-"+code+"-"+grade_money.getString(j)))+"</td>";
                tr2+="<td class='color"+code+"'>"+grade_money.getString(j)+"</td>";
            }
        }
        tr0+="</tr>";
        tr1+="</tr>";
        tr2+="</tr>";
        model.addAttribute("width",width);
        model.addAttribute("tr0",tr0);
        model.addAttribute("tr1",tr1);
        model.addAttribute("tr2",tr2);
        model.addAttribute("match",match);
        model.addAttribute("list",mapList);
        model.addAttribute("rule",ruleList);
        return "wap/registList2";
    }
    @RequestMapping(value = "registList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<List<Map>> registList(String match_id,Model model){
        if (loger.isDebugEnabled())
            loger.debug(NodejsMatchRegistController.class + "/wap/registList->");
        Map param=new HashMap();
        param.put("match_id",match_id);
        Map queryMap=new HashMap();
        queryMap.put("queryMap",param);
        List<NodejsMatchRegist> list=nodejsMatchRegistService.selectListByDynamic(queryMap);
        List<Map> mapList = new ArrayList<Map>();
        for (NodejsMatchRegist regist : list) {
            try {
                Map describe = BeanUtils.describe(regist);
                String registJson = regist.getRegist();
                Map<String,String> json = JSONObject.parseObject(registJson, Map.class);
                json.entrySet().forEach(item->item.setValue("✔"));
                describe.putAll(json);
                mapList.add(describe);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return BaseResponse.ToJsonResult(mapList);
    }

    /**
     * 我的比赛成绩页面
     *
     * @param model
     * @param cote_id
     * @param model
     * @return
     * @throws WxErrorException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/page/match")
    public String match(String cote_id, ModelMap model) throws Exception {
        if (loger.isDebugEnabled())
            loger.debug(WapController.class + "/page/match->" + cote_id);
        NodejsCrawlerCoteExtend cote = nodejsCrawlerCoteExtendService.selectByCoteId(cote_id);
        Map param=new HashedMap();
        Map queryMap=new HashedMap();
        Map page=new HashedMap();
        queryMap.put("cote_id",cote.getCote_id());
        //queryMap.put("match_status",2);
        page.put("offset",0);
        page.put("pageSize",1);
        param.put("queryMap",queryMap);
        param.put("page",page);
        param.put("sort","start_time ");
        List<NodejsMatch> matchList = nodejsMatchService.selectListByDynamic(param);
        if (matchList!=null&&matchList.size()>0){
            model.put("match", matchList.get(0));
        }
        model.put("cote", cote);
        return "wap/match";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/page/matchSign")
    public String matchSign(String match_id,@RequestParam(name = "code") String code, @RequestParam(name = "state") String returnUrl,Model model) throws WxErrorException {
        NodejsMatch match = nodejsMatchService.selectOneById(match_id);
        String match_rule = match.getRule();
        JSONArray ruleArray = JSONObject.parseArray(match_rule);
        List<Map> rule=new ArrayList<>();
        for (int i = 0; i < ruleArray.size(); i++) {
            JSONObject jsonObject = ruleArray.getJSONObject(i).getJSONObject("rule");
            String code1 = jsonObject.getString("code");
            String name = jsonObject.getString("name");
            JSONArray grade_money = jsonObject.getJSONArray("grade_money");
            for (int j = 0; j < grade_money.size(); j++) {
                Map map=new HashedMap();
                map.put("name",name+"  "+grade_money.getString(j)+"组");
                map.put("code","field-"+code1+"-"+grade_money.getString(j));
                rule.add(map);
            }
        }


        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        String openId = wxMpOAuth2AccessToken.getOpenId();
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
        if (wxMpUser != null && wxMpUser.getSubscribe()) {
            WeixinUser weixinUser = weixinUserService.selectOneById(openId);
            if (weixinUser==null){
                model.addAttribute("code","请先进行用户绑定，谢谢！");
                return "wap/matchSign";
            }
            String bind_tel = weixinUser.getBind_tel();
            Map param=new HashedMap();
            Map queryMap=new HashedMap();
            queryMap.put("mobile",bind_tel);
            param.put("queryMap",queryMap);
            List<NodejsMobileUser> mobileUserList = nodejsMobileUserService.selectListByDynamic(param);
            if(mobileUserList==null||mobileUserList.size()==0){
                model.addAttribute("code","集鸽名单中不存在您的鸽子！");
                return "wap/matchSign";
            }
            String pigowner = mobileUserList.get(0).getPigowner();
            param.clear();
            param.put("pigowner",pigowner);
            param.put("match_id",match_id);
            List pigeonList = nodejsMatchPigeonCollectionService.selectListBySign(param);
            model.addAttribute("list",pigeonList);
            model.addAttribute("match",match);
            model.addAttribute("rule",rule);
        } else {
            String redirectURL = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI1NDk4NzYzMw==&scene=126#wechat_redirect";
            return "redirect:" + redirectURL;
        }
        return "wap/matchSign";
    }
    /**
     * 插入操作
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "/sign_up_insert", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Integer> insertOperation(@RequestBody List<NodejsMatchRegist> body, HttpServletRequest request, HttpServletResponse response) {
        if (loger.isDebugEnabled())
            loger.debug(NodejsMatchRegistController.class + "/insert->" + JSON.toJSONString(body));

        List<NodejsMatchRegist> item = body;
        SSOToken ssoToken = SSOHelper.getSSOToken(request);
        int startMoney=0,endMoney=0;
        if(body!=null&&body.size()>0){
            startMoney+=body.get(0).getReward();
            endMoney+=body.get(0).getRank();
        }
        int result=0;
        if (ssoToken != null) {
            NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);
            for (NodejsMatchRegist r : item) {
                if (r.getId()==null||r.getId().equals("")){
                    String id = SnGenerator.getSn("MR");
                    r.setId(id);
                    r.setCreate_user_id(nodejsSysUser.getId());
                    r.setCote_id(nodejsSysUser.getCote_id());
                    r.setCote_name(nodejsSysUser.getCote_name());
                    r.setCreate_time(new Date());
                    r.setModify_time(new Date());
                    result+=nodejsMatchRegistService.insert(r);
                }else {
                    if (r.getRegist()!=null&&r.getRegist().length()>7){

                        result+=nodejsMatchRegistService.update(r);
                    }else {
                        result+=nodejsMatchRegistService.deleteById(r.getId());
                    }
                }
            }
        }
        return BaseResponse.ToJsonResult(result);
    }
    @RequestMapping("resultRealTime")
    public String resultRealTime(String match_id, HttpServletRequest request, Model model) {
        //根据id获取比赛
        NodejsMatch nodejsMatch = nodejsMatchService.selectOneById(match_id);
        Map map = new HashedMap();
        map.put("match_id", match_id);
        //获取比赛报名表
        Map queryMap = new HashedMap();
        queryMap.put("queryMap", map);
        List<NodejsMatchRegist> matchRegistList = nodejsMatchRegistService.selectListByDynamic(queryMap);
        String match_rule = nodejsMatch.getRule();
        JSONArray ruleArray = JSONObject.parseArray(match_rule);
        //遍历比赛规则
        List result = new ArrayList();
        for (int i = 0; i < ruleArray.size(); i++) {
            JSONObject rule = ruleArray.getJSONObject(i).getJSONObject("rule");
            String rule_code = rule.getString("code");
            JSONArray grade_money = rule.getJSONArray("grade_money");
            for (int j = 0; j < grade_money.size(); j++) {
                float money = grade_money.getFloat(j);
                //表明表中某一规则某一档次的所有报名的鸽子
                List<NodejsMatchRegist> filterList = matchRegistList.stream().filter(regist -> regist.getRegist().contains("\"field-" + rule_code + "-" + (int) money + "\"")).collect(Collectors.toList());
                filterList.stream().forEach(regist -> {
                    if (regist.getRank() <= 0) {
                        regist.setRank(999999999);
                    }
                });
                filterList = filterList.stream().sorted((b1, b2) -> b1.getRank() - b2.getRank()).collect(Collectors.toList());
                Map echoResult = nodejsMatchRegistService.echoResultRealTime(rule, money, filterList, false);
                if (echoResult.size() > 0) {
                    result.add(echoResult);
                }
            }

        }
        model.addAttribute("match", nodejsMatch);
        model.addAttribute("list", result);
        return "nodejsMatch/match_result";
    }

}
