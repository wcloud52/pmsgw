package com.saas.biz.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.saas.biz.mapper.impl.NodejsMatchRegistImplMapper;
import com.saas.biz.pojo.NodejsMatchRegist;
import com.saas.biz.service.NodejsMatchRegistService;
import com.saas.common.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NodejsMatchRegistServiceImpl implements NodejsMatchRegistService {
	@Autowired
	private NodejsMatchRegistImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(NodejsMatchRegist record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(NodejsMatchRegist record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(String id) {
		return implMapper.deleteByPrimaryKey(id);
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public NodejsMatchRegist selectOneById(String id) {
		return implMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取全部记录
	 * @return
	 */
	@Override
	public List<NodejsMatchRegist> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<NodejsMatchRegist> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsMatchRegist>() {
			@Override
			public List<NodejsMatchRegist> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	/**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	@Override
	public List<NodejsMatchRegist> selectListByDynamic(Map<Object, Object> paraMap) {
		return implMapper.selectListByDynamic(paraMap);
	}
	/**
	 * 动态查询总数
	 * @param paraMap
	 * @return
	 */
	@Override
	public long selectCountByDynamic(Map<Object, Object> paraMap) {
		return implMapper.selectCountByDynamic(paraMap);
	}
	/**
	 * 根据主键列表删除
	 * @param ids
	 * @return
	 */
	@Override
	public int deleteByIds(List<String> ids) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		String idString = StringUtils.collectionToDelimitedString(ids, ",");
		nodes.add(new QueryNode("id", OpEnum.IN.getName(),PrependEnum.AND.getName(), idString));
		int result = DynamicDelete.deleteByDynamic(nodes, new DynamicDeleteSpecification() {
			@Override
			public int deleteByDynamic(Map<Object, Object> map) {
				return implMapper.deleteByDynamic(map);
			}
		});
		return result;
	}
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	@Override
	public int insertBatch(List<NodejsMatchRegist> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<NodejsMatchRegist> list)
	{
		return implMapper.updateBatch(list);
	}

	/**
	 * 比赛结束后根据比赛结果更新报名表
	 * @param paraMap
	 * @return
	 */
	@Override
	public int updateRegistRank(Map<Object, Object> paraMap){return implMapper.updateRegistRank(paraMap);}
	/**
	 * 更新单个鸽子的奖金（）
	 * @param list
	 */
	@Override
	public int updateSumReward(List<NodejsMatchRegist> list){return implMapper.updateSumReward(list);}
	public List<NodejsMatchRegist> selectCrawlerDetailGame(Map<Object, Object> paraMap){return implMapper.selectCrawlerDetailGame(paraMap);}
	@Override
	public Map echoResult(JSONObject rule, float money, List<NodejsMatchRegist> rankLst, boolean update) {
		Map result = new HashedMap();
		String rule_name = rule.getString("name");
		String rule_code = rule.getString("code");
		int count = rankLst.size();
		List<NodejsMatchRegist> list = new ArrayList<>();
		try {
			switch (rule_code) {
				case "1":
					float reward = rule.getFloat("reward");
					List<NodejsMatchRegist> subList = new ArrayList<>(rankLst.subList(0, 1));
					list=deepCopy(subList);
					list.get(0).setReward(reward / 100 * count*money);
					result.put("list", list);
					result.put("name", rule_name + (int)money + "组（" + count + "羽 取1名）");
					break;
				case "2":
					JSONArray grades = rule.getJSONArray("grades");
					int end_rank = 0, end_rank_index = 0;
					for (int i = 0; i < grades.size(); i++) {
						try {
							end_rank = grades.getJSONObject(i).getInteger("end_rank");
							if (end_rank >= count) {
								end_rank_index = i;
								break;
							}
						} catch (Exception e) {
							end_rank_index = i;
							break;
						}
					}
					JSONArray rewards = grades.getJSONObject(end_rank_index).getJSONArray("reward");
					List<Float> collect = rewards.stream().map(item ->
							Float.parseFloat(item + "") / 100
					).collect(Collectors.toList());
					subList = new ArrayList<>(rankLst.subList(0, collect.size()));
					list=deepCopy(subList);
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setReward(collect.get(i) * money*count);
					}
					result.put("list", list);
					result.put("name", rule_name +(int) money + "组（" + count + "羽 取" + list.size() + "名）");
					break;
				case "3":
				case "4":
				case "5":
				case "6":
				case "7":
					int rank = Integer.parseInt(rule_name.split("取")[0]);
					int top = count / rank;
					subList = new ArrayList<>(rankLst.subList(0, top));
					list=deepCopy(subList);
					int pow=(int)(rank/Math.pow(10,(rank+"").length()-1))*(int)Math.pow(10,(rank+"").length()-1);
					list.stream().forEach(item -> item.setReward(money * pow));
					if (count % rank > 0) {
						NodejsMatchRegist newRegist = new NodejsMatchRegist();
						newRegist.setRank(rankLst.get(top).getRank());
						newRegist.setPigeon_code(rankLst.get(top).getPigeon_code());
						newRegist.setMember_code(rankLst.get(top).getMember_code());
						newRegist.setMember_name(rankLst.get(top).getMember_name());
						newRegist.setReward((count - top * rank) * money * 0.9f);
						list.add(newRegist);
						top++;
					}
					result.put("list", list);
					result.put("name", rule_name + " " + (int)money + "组（" + count + "羽 取" + top + "名）");
					break;
				case "8":
				case "9":
					JSONArray grade_money = rule.getJSONArray("grade_money");
					JSONArray ranks = rule.getJSONArray("ranks");
					int grade_money_index=0;
					for (int i = 0; i < grade_money.size(); i++) {
						if (grade_money.getFloat(i)==(money)){
							grade_money_index=i;
							break;
						}
					}

					rank=ranks.getInteger(grade_money_index);
					top = count / rank;
					subList = new ArrayList<>(rankLst.subList(0, top));
					list=deepCopy(subList);
					list.stream().forEach(item -> item.setReward(money * (rank - 1)));
					if (count % rank > 0) {
						NodejsMatchRegist newRegist = new NodejsMatchRegist();
						newRegist.setRank(rankLst.get(top).getRank());
						newRegist.setPigeon_code(rankLst.get(top).getPigeon_code());
						newRegist.setMember_code(rankLst.get(top).getMember_code());
						newRegist.setMember_name(rankLst.get(top).getMember_name());
						newRegist.setReward((count - top * rank) * money * 0.9f);
						list.add(newRegist);
						top++;
					}
					result.put("list", list);
					result.put("name", rule_name + " " + (int)money + "组（" + count + "羽 取" + top + "名）");
					break;
				case "10":
					rank = rule.getInteger("rank");
					reward = rule.getFloat("reward");
					rankLst=new ArrayList<>(rankLst.stream().filter(item->item.getRank()<=rank).collect(Collectors.toList()));
					list=deepCopy(rankLst);
					int c=list.size();
					list.stream().forEach(item -> item.setReward(0f+(int)(money * count * reward / 100 /c )));
					result.put("list", list);
					result.put("name", rule_name + " " + (int)money + "组（" + count + "羽 取" + list.size() + "名）");
					break;
				case "11":
					rank = rule.getInteger("rank");
					reward = rule.getFloat("reward");
					list = new ArrayList<>();
					for (NodejsMatchRegist r : rankLst) {
						if (r.getRank() >= rank) {
							NodejsMatchRegist newRegist = new NodejsMatchRegist();
							newRegist.setRank(r.getRank());
							newRegist.setPigeon_code(r.getPigeon_code());
							newRegist.setMember_code(r.getMember_code());
							newRegist.setMember_name(r.getMember_name());
							newRegist.setReward(count * money * 0.85f);
							list.add(newRegist);
							break;
						}
					}
					result.put("list", list);
					result.put("name", rule_name + (int)money + "组（" + count + "羽 取1名）");
					break;
			}
			if (update) {
				updateSumReward(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public Map echoResultRealTime(JSONObject rule, float money, List<NodejsMatchRegist> rankLst, boolean update) {
		Map result = new HashedMap();
		String rule_name = rule.getString("name");
		String rule_code = rule.getString("code");
		int count = rankLst.size();
		List<NodejsMatchRegist> list = new ArrayList<>();
		List<NodejsMatchRegist> subList=new ArrayList();
		if (rankLst!=null&&rankLst.size()>0) {
			try {
				switch (rule_code) {
					case "1":
						float reward = rule.getFloat("reward");
						if (rankLst.get(0).getRank() != 999999999) {
							subList = new ArrayList<>(rankLst.subList(0, 1));
							list = deepCopy(subList);
							list.get(0).setReward(reward / 100 * count * money);
						}
						result.put("list", list);
						result.put("name", rule_name + (int) money + "组（" + count + "羽 取1名）");
						break;
					case "2":
						JSONArray grades = rule.getJSONArray("grades");
						int end_rank = 0, end_rank_index = 0;
						for (int i = 0; i < grades.size(); i++) {
							try {
								end_rank = grades.getJSONObject(i).getInteger("end_rank");
								if (end_rank >= count) {
									end_rank_index = i;
									break;
								}
							} catch (Exception e) {
								end_rank_index = i;
								break;
							}
						}
						JSONArray rewards = grades.getJSONObject(end_rank_index).getJSONArray("reward");
						List<Float> collect = rewards.stream().map(item ->
								Float.parseFloat(item + "") / 100
						).collect(Collectors.toList());
						result.put("name", rule_name + (int) money + "组（" + count + "羽 取" + collect.size() + "名）");
						rankLst = new ArrayList<>(rankLst.stream().filter(item -> item.getRank() != 999999999).collect(Collectors.toList()));
						if (rankLst.size() > 0) {
							if (collect.size() >= rankLst.size()) {
								collect = collect.subList(0, rankLst.size());
							}
							subList = new ArrayList<>(rankLst.subList(0, collect.size()));
							list = deepCopy(subList);
							for (int i = 0; i < list.size(); i++) {
								list.get(i).setReward(collect.get(i) * money * count);
							}
						}
						result.put("list", list);
						break;
					case "3":
					case "4":
					case "5":
					case "6":
					case "7":
						int rank = Integer.parseInt(rule_name.split("取")[0]);
						int top = count / rank;
						rankLst = new ArrayList<>(rankLst.stream().filter(item -> item.getRank() != 999999999).collect(Collectors.toList()));
						result.put("name", rule_name + " " + (int) money + "组（" + count + "羽 取" + (count % rank > 0?top+1:top) + "名）");
						if (rankLst.size() > 0) {
							if (rankLst.size() < top) {
								top = rankLst.size();
							}
							subList = new ArrayList<>(rankLst.subList(0, top));
							list = deepCopy(subList);
							int pow = (int) (rank / Math.pow(10, (rank + "").length() - 1)) * (int) Math.pow(10, (rank + "").length() - 1);
							list.stream().forEach(item -> item.setReward(money * pow));
							if (count % rank > 0) {
								NodejsMatchRegist newRegist = new NodejsMatchRegist();
								newRegist.setRank(rankLst.get(top).getRank());
								newRegist.setPigeon_code(rankLst.get(top).getPigeon_code());
								newRegist.setMember_code(rankLst.get(top).getMember_code());
								newRegist.setMember_name(rankLst.get(top).getMember_name());
								newRegist.setReward((count - top * rank) * money * 0.9f);
								list.add(newRegist);
								top++;
							}
						}
						result.put("list", list);
						break;
					case "8":
					case "9":
						JSONArray grade_money = rule.getJSONArray("grade_money");
						JSONArray ranks = rule.getJSONArray("ranks");
						int grade_money_index = 0;
						for (int i = 0; i < grade_money.size(); i++) {
							if (grade_money.getFloat(i) == (money)) {
								grade_money_index = i;
								break;
							}
						}
						rank = ranks.getInteger(grade_money_index);
						top = count / rank;
						result.put("name", rule_name + " " + (int) money + "组（" + count + "羽 取" + (count % rank > 0?top+1:top) + "名）");
						rankLst = new ArrayList<>(rankLst.stream().filter(item -> item.getRank() != 999999999).collect(Collectors.toList()));
						if (rankLst.size() > 0) {
							if (rankLst.size() < top) {
								top = rankLst.size();
							}
							subList = new ArrayList<>(rankLst.subList(0, top));
							list = deepCopy(subList);
							list.stream().forEach(item -> item.setReward(money * (rank - 1)));
							if (count % rank > 0) {
								NodejsMatchRegist newRegist = new NodejsMatchRegist();
								newRegist.setRank(rankLst.get(top).getRank());
								newRegist.setPigeon_code(rankLst.get(top).getPigeon_code());
								newRegist.setMember_code(rankLst.get(top).getMember_code());
								newRegist.setMember_name(rankLst.get(top).getMember_name());
								newRegist.setReward((count - top * rank) * money * 0.9f);
								list.add(newRegist);
								top++;
							}
						}
						result.put("list", list);
						break;
					case "10":
						rank = rule.getInteger("rank");
						reward = rule.getFloat("reward");
						rankLst = new ArrayList<>(rankLst.stream().filter(item -> item.getRank() <= rank).collect(Collectors.toList()));
						if (rankLst.size()>0) {
							list = deepCopy(rankLst);
							int c = list.size();
							list.stream().forEach(item -> item.setReward(0f + (int) (money * count * reward / 100 / c)));
						}
						result.put("list", list);
						result.put("name", rule_name + " " + (int) money + "组（" + count + "羽 取前" + rank + "名）");
						break;
					case "11":
						rank = rule.getInteger("rank");
						reward = rule.getFloat("reward");
						list = new ArrayList<>();
						rankLst = new ArrayList<>(rankLst.stream().filter(item -> item.getRank() != 999999999).collect(Collectors.toList()));
						if (rankLst.size() > 0) {
							for (NodejsMatchRegist r : rankLst) {
								if (r.getRank() >= rank) {
									NodejsMatchRegist newRegist = new NodejsMatchRegist();
									newRegist.setRank(r.getRank());
									newRegist.setPigeon_code(r.getPigeon_code());
									newRegist.setMember_code(r.getMember_code());
									newRegist.setMember_name(r.getMember_name());
									newRegist.setReward(count * money * 0.85f);
									list.add(newRegist);
									break;
								}
							}
						}
						result.put("list", list);
						result.put("name", rule_name + (int) money + "组（" + count + "羽 取1名）");
						break;
				}
				if (update) {
					updateSumReward(list);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	@Override
	public Map<String,Object> echoOneResult(JSONObject rule,String key, List<NodejsMatchRegist> rankLst,String pigeon_code) {
		String[] split = key.split("-");
		int count = rankLst.size();
		String rule_name = rule.getString("name");
		Map<String,Object> result=new HashedMap();
		result.put("reward",0f);
		try {
			float money=Float.parseFloat(split[2]);
			switch (split[1]) {
				case "1":
					float reward = rule.getFloat("reward");
					List<NodejsMatchRegist> subList = new ArrayList<>(rankLst.subList(0, 1));
					List<NodejsMatchRegist> list = subList.stream().filter(item -> item.getPigeon_code().equals(pigeon_code)).collect(Collectors.toList());
					if (list.size()>0){
						result.put("reward",reward / 100f * count*money);
					}
					result.put("name", rule_name + (int)money + "组");
					break;
				case "2":
					JSONArray grades = rule.getJSONArray("grades");
					int end_rank = 0, end_rank_index = 0;
					for (int i = 0; i < grades.size(); i++) {
						try {
							end_rank = grades.getJSONObject(i).getInteger("end_rank");
							if (end_rank >= count) {
								end_rank_index = i;
								break;
							}
						} catch (Exception e) {
							end_rank_index = i;
							break;
						}
					}
					JSONArray rewards = grades.getJSONObject(end_rank_index).getJSONArray("reward");
					List<Float> collect = rewards.stream().map(item ->
							Float.parseFloat(item + "") / 100
					).collect(Collectors.toList());
					subList = new ArrayList<>(rankLst.subList(0, collect.size()));
					list=deepCopy(subList);
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getPigeon_code().equals(pigeon_code)){
							result.put("reward",collect.get(i) * money*count);
							break;
						}
					}
					result.put("name", rule_name +(int) money + "组");
					break;
				case "3":
				case "4":
				case "5":
				case "6":
				case "7":
					int rank = Integer.parseInt(rule.getString("name").split("取")[0]);
					int top = count / rank;
					subList = new ArrayList<>(rankLst.subList(0, top));
					list=deepCopy(subList);
					int pow=(int)(rank/Math.pow(10,(rank+"").length()-1))*(int)Math.pow(10,(rank+"").length()-1);
					list.stream().forEach(item -> item.setReward(money * pow));
					if (count % rank > 0) {
						NodejsMatchRegist newRegist = new NodejsMatchRegist();
						newRegist.setRank(rankLst.get(top).getRank());
						newRegist.setPigeon_code(rankLst.get(top).getPigeon_code());
						newRegist.setMember_code(rankLst.get(top).getMember_code());
						newRegist.setMember_name(rankLst.get(top).getMember_name());
						newRegist.setReward((count - top * rank) * money * 0.9f);
						list.add(newRegist);
						top++;
					}
					List<NodejsMatchRegist> registList = list.stream().filter(item -> item.getPigeon_code().equals(pigeon_code)).collect(Collectors.toList());
					if (registList.size()>0){
						result.put("reward",registList.get(0).getReward());
					}
					result.put("name", rule_name + " " + (int)money + "组");
					break;
				case "8":
				case "9":
					JSONArray grade_money = rule.getJSONArray("grade_money");
					JSONArray ranks = rule.getJSONArray("ranks");
					int grade_money_index=0;
					for (int i = 0; i < grade_money.size(); i++) {
						if (grade_money.getFloat(i)==(money)){
							grade_money_index=i;
							break;
						}
					}
					rank=ranks.getInteger(grade_money_index);
					top = count / rank;
					subList = new ArrayList<>(rankLst.subList(0, top));
					list=deepCopy(subList);
					list.stream().forEach(item -> item.setReward(money * (rank - 1)));
					if (count % rank > 0) {
						NodejsMatchRegist newRegist = new NodejsMatchRegist();
						newRegist.setRank(rankLst.get(top).getRank());
						newRegist.setPigeon_code(rankLst.get(top).getPigeon_code());
						newRegist.setMember_code(rankLst.get(top).getMember_code());
						newRegist.setMember_name(rankLst.get(top).getMember_name());
						newRegist.setReward((count - top * rank) * money * 0.9f);
						list.add(newRegist);
						top++;
					}
					registList = list.stream().filter(item -> item.getPigeon_code().equals(pigeon_code)).collect(Collectors.toList());
					if (registList.size()>0){
						result.put("reward",registList.get(0).getReward());
					}
					result.put("name", rule_name + " " + (int)money + "组");
					break;
				case "10":
					rank = rule.getInteger("rank");
					reward = rule.getFloat("reward");
					if (rankLst.size()>0&&rankLst.get(rankLst.size()-1).getRank()>=rank) {
						rankLst = new ArrayList<>(rankLst.stream().filter(item -> item.getRank() <= rank).collect(Collectors.toList()));
						list = deepCopy(rankLst);
						int c = list.size();
						list.stream().forEach(item -> item.setReward(0f + (int) (money * count * reward / 100 / c)));
						registList = list.stream().filter(item -> item.getPigeon_code().equals(pigeon_code)).collect(Collectors.toList());
						if (registList.size() > 0) {
							result.put("reward", registList.get(0).getReward());
						}
					}
					result.put("name", rule_name + " " + (int)money + "组");
					break;
				case "11":
					rank = rule.getInteger("rank");
					reward = rule.getFloat("reward");
					list = new ArrayList<>();
					for (NodejsMatchRegist r : rankLst) {
						if (r.getRank() >= rank) {
							NodejsMatchRegist newRegist = new NodejsMatchRegist();
							newRegist.setRank(r.getRank());
							newRegist.setPigeon_code(r.getPigeon_code());
							newRegist.setMember_code(r.getMember_code());
							newRegist.setMember_name(r.getMember_name());
							newRegist.setReward(count * money * 0.85f);
							list.add(newRegist);
							break;
						}
					}
					registList = list.stream().filter(item -> item.getPigeon_code().equals(pigeon_code)).collect(Collectors.toList());
					if (registList.size()>0){
						result.put("reward",registList.get(0).getReward());
					}
					result.put("name", rule_name + (int)money + "组");
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		@SuppressWarnings("unchecked")
		List<T> dest = (List<T>) in.readObject();
		return dest;
	}

}
