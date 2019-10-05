package com.saas.biz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.biz.pojo.*;
import com.saas.biz.service.NodejsCrawlerService;
import com.saas.biz.service.NodejsMatchRegistService;
import com.saas.biz.service.NodejsMatchService;
import com.saas.biz.util.SnGenerator;
import com.saas.common.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/nodejsMatch")
public class NodejsMatchRegistController {
    protected static final Logger log = LoggerFactory.getLogger(NodejsMatchRegistController.class);

    @Autowired
    protected NodejsMatchRegistService sv;
    @Autowired
    protected NodejsMatchService nodejsMatchService;
    @Autowired
    protected NodejsCrawlerService nodejsCrawlerService;


    @RequestMapping(value = "/sign_up_list", method = RequestMethod.POST)
    @ResponseBody
    public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        if (log.isDebugEnabled())
            log.debug(NodejsMatchRegistController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);
        List<QueryNode> nodes = new ArrayList<QueryNode>();
        SSOToken ssoToken = SSOHelper.getSSOToken(request);
        if (ssoToken != null) {
            NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);
            nodes.add(new QueryNode("cote_id", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), nodejsSysUser.getCote_id()));
        }
        QueryNodes queryNode = QueryNodes.createQueryNodes(nodes, "and");
        List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);
        queryNodes.add(queryNode);
        QueryObject query = new QueryObject();
        query.setPageIndex(pageIndex);
        query.setPageSize(pageSize);
        query.setSort(sort);
        query.setFuzzyQuery(queryNodes);
        BaseResponse<JsonResult<List<NodejsMatchRegist>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsMatchRegist>() {
            @Override
            public List<NodejsMatchRegist> query(Map<Object, Object> map) {
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
        List<NodejsMatchRegist> list = restult.getData().getList();
        List<Map> mapList = new ArrayList<Map>();
        for (NodejsMatchRegist regist : list) {
            try {
                Map describe = BeanUtils.describe(regist);
                String registJson = regist.getRegist();
                Map<String,String> json = JSONObject.parseObject(registJson, Map.class);
                int sum=0;
                for(String value : json.values()){
                    try {
                        sum+= Integer.parseInt(value);
                    }catch (Exception e){

                    }
                }
                json.entrySet().forEach(item->item.setValue("✔"));
                json.put("sum",sum+"");
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
        long count = restult.getData().getTotal();
        int code = restult.getCode();
        String msg = restult.getMessage();

        query.setPageIndex(1);
        query.setPageSize((int)count+50);
        BaseResponse<JsonResult<List<NodejsMatchRegist>, Object>> all = PagingAndSortingRepository.find(query, new PageSpecification<NodejsMatchRegist>() {
            @Override
            public List<NodejsMatchRegist> query(Map<Object, Object> map) {
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
        list = all.getData().getList();
        Map<String,Integer> desc=new HashedMap();
        for (NodejsMatchRegist regist :list ) {
            try {
                Map describe = BeanUtils.describe(regist);
                String registJson = regist.getRegist();
                Map<String,String> json = JSONObject.parseObject(registJson, Map.class);
                json.entrySet().forEach(item->{
                    if (desc.get(item.getKey())==null){
                        desc.put(item.getKey(),1);
                    }else {
                        desc.put(item.getKey(),desc.get(item.getKey())+1);
                    }
                });
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> descObj = new HashMap<Object, Object>();
        descObj.putAll(desc);
        descObj.put("pigeon_code","总羽数");
        mapList.add(0,descObj);
        map.put("code", code);
        map.put("msg", msg);
        map.put("count", count);
        map.put("data", mapList);
        map.put("desc",desc);
        return map;
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
        if (log.isDebugEnabled())
            log.debug(NodejsMatchRegistController.class + "/insert->" + JSON.toJSONString(body));
        List<NodejsMatchRegist> item = body;
        SSOToken ssoToken = SSOHelper.getSSOToken(request);
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
                    result+=sv.insert(r);
                }else {
                    if (r.getRegist()!=null&&r.getRegist().length()>7){

                        result+=sv.update(r);
                    }else {
                        result+=sv.deleteById(r.getId());
                    }
                }
            }

        }
        sv.sendMessage(body);
        return BaseResponse.ToJsonResult(result);
    }


    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(value = "/query/sign_up_list", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<List<NodejsMatchRegist>> queryListOperation() {
        if (log.isDebugEnabled())
            log.debug(NodejsMatchRegistController.class + "/list->");
        List<NodejsMatchRegist> list = sv.selectAll();
        return BaseResponse.ToJsonResult(list);
    }

    /**
     * 列表条件查询
     *
     * @param queryObject
     * @return
     */
    @RequestMapping(value = "/query/sign_up_listBy", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<List<NodejsMatchRegist>> queryListByOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
        if (log.isDebugEnabled())
            log.debug(NodejsMatchRegistController.class + "/listBy->" + JSON.toJSONString(queryObject));

        List<NodejsMatchRegist> list = PagingAndSortingRepository.findList(queryObject.getJson(), new Specification<NodejsMatchRegist>() {

            @Override
            public List<NodejsMatchRegist> query(Map<Object, Object> map) {
                return sv.selectListByDynamic(map);
            }

            @Override
            public Object queryExt(Map<Object, Object> map) {
                return null;
            }

        });
        return BaseResponse.ToJsonResult(list);
    }


    /**
     * 单条查询
     *
     * @param match_id
     * @return
     */
    @RequestMapping(value = "/query/sign_up_one", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<NodejsMatchRegist> queryOneOperation(String match_id) {
        if (log.isDebugEnabled())
            log.debug(NodejsMatchRegistController.class + "/one->" + match_id);
        NodejsMatchRegist t = sv.selectOneById(match_id);
        return BaseResponse.ToJsonResult(t);
    }


    /**
     * 删除操作
     *
     * @param match_id
     * @return
     */
    @RequestMapping(value = "/delete/sign_up_one/{id}", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Integer> deleteOperation(@PathVariable("id") String match_id) {
        if (log.isDebugEnabled())
            log.debug(NodejsMatchRegistController.class + "/delete/{match_id}->" + match_id);

        int result = sv.deleteById(match_id);
        return BaseResponse.ToJsonResult(result);
    }

    /**
     * 删除操作
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete/sign_up_listBy", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Integer> deleteListOperation(@RequestBody List<String> ids) {
        if (log.isDebugEnabled())
            log.debug(NodejsMatchRegistController.class + "/delete->" + JSON.toJSONString(ids));

        int result = sv.deleteByIds(ids);
        return BaseResponse.ToJsonResult(result);
    }

    /**
     * 比赛结束后根据比赛结果更新报名表
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "/updateRegistRank", method = RequestMethod.POST)
    @ResponseBody
    public void updateRegistRank(HttpServletRequest request, @RequestBody NodejsMatch body) {
        SSOToken ssoToken = SSOHelper.getSSOToken(request);
        Map map = new HashedMap();
        if (ssoToken != null) {
            NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);
            NodejsMatch nodejsMatch = nodejsMatchService.selectOneById(body.getMatch_id());
            map.put("cote_id", nodejsSysUser.getCote_id());
            map.put("match_id", nodejsMatch.getMatch_id());
            map.put("start_time", nodejsMatch.getStart_time());
            map.put("end_time", nodejsMatch.getEnd_time());
            map.put("database", "pmsgw_weixin");
            int i = sv.updateRegistRank(map);
            System.out.println(i);
        }
    }

    @RequestMapping("result")
    public String selectResult(String match_id, HttpServletRequest request, Model model) {
        //根据id获取比赛
        NodejsMatch nodejsMatch = nodejsMatchService.selectOneById(match_id);
        Map map = new HashedMap();
        map.put("match_id", match_id);
        //获取比赛报名表
        Map queryMap = new HashedMap();
        queryMap.put("queryMap", map);
        List<NodejsMatchRegist> matchRegistList = sv.selectListByDynamic(queryMap);
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
                Map echoResult = sv.echoResultRealTime(rule, money, filterList, false);
                if (echoResult.size() > 0) {
                    result.add(echoResult);
                }
            }

        }
        model.addAttribute("match", nodejsMatch);
        model.addAttribute("list", result);
        return "nodejsMatch/match_result";
    }

    @RequestMapping(value = "page/registList",method = RequestMethod.GET)
    public String registListPage(NodejsMatchRegist nodejsMatchRegist,Model model){
        if (log.isDebugEnabled())
            log.debug(NodejsMatchRegistController.class + "/match/registList->");
        Map param=new HashMap();
        param.put("match_id",nodejsMatchRegist.getMatch_id());
        NodejsMatch match = nodejsMatchService.selectOneById(nodejsMatchRegist.getMatch_id());
        param.clear();
        param.put("match_id",nodejsMatchRegist.getMatch_id());
        if(nodejsMatchRegist.getCreate_user_id()!=null&&!nodejsMatchRegist.getCreate_user_id().equals("")){
            param.put("create_user_id",nodejsMatchRegist.getCreate_user_id());
        }
        if (nodejsMatchRegist.getMember_name()!=null&&!nodejsMatchRegist.getMember_name().equals("")){
            param.put("member_name",nodejsMatchRegist.getMember_name());
        }
        Map queryMap=new HashMap();
        queryMap.put("queryMap",param);

        List<NodejsMatchRegist> list=sv.selectListByDynamic(queryMap);
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
            tr1+="<td colspan='"+grade_money.size()+"'>"+name+"</td>";
            for (int j = 0; j < grade_money.size(); j++) {
                width+=80;
                ruleList.add("field-"+code+"-"+grade_money.getString(j));
                titleList.put("field-"+code+"-"+grade_money.getString(j),name+"    "+grade_money.getString(j)+"组");
                tr0+="<td>"+(desc.get("field-"+code+"-"+grade_money.getString(j))==null?0:desc.get("field-"+code+"-"+grade_money.getString(j)))+"</td>";
                tr2+="<td>"+grade_money.getString(j)+"</td>";
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
        return "nodejsMatch/registList2";
    }

}