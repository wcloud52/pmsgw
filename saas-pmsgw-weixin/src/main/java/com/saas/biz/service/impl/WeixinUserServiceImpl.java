package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.biz.mapper.ext.WeixinUserExtMapper;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.WeixinUserService;

@Service
public class WeixinUserServiceImpl implements WeixinUserService {
    @Autowired
    private WeixinUserExtMapper weixinUserExtMapper;


    @Override
    public WeixinUser selectOneById(String id) {

        return weixinUserExtMapper.selectByPrimaryKey (id);
    }

    @Transactional
    @Override
    public int insert(WeixinUser record) {
        return weixinUserExtMapper.insert (record);
    }

    @Override
    public int update(WeixinUser record) {
        return weixinUserExtMapper.updateByPrimaryKeySelective (record);
    }

    @Override
    public List <WeixinUser> selectListByDynamic(Map <Object, Object> paraMap) {

        return weixinUserExtMapper.selectListByDynamic (paraMap);
    }

    @Override
    public long selectCountByDynamic(Map <Object, Object> paraMap) {

        return weixinUserExtMapper.selectCountByDynamic (paraMap);
    }

    @Cacheable(cacheNames = "weixin_user", key = "'weixin_user_selectAll'", sync = true)
    @Override
    public List <WeixinUser> selectAll() {
        return weixinUserExtMapper.selectAllList ();
    }

    //公棚
    @Cacheable(cacheNames = "weixin_user", key = "'weixin_user_selectAllWithMapByLoft'", sync = true)
    @Override
    public Map <String, List <WeixinUser>> selectAllWithMapByLoft() {
        List <WeixinUser> listAll = selectAll ();

        List <WeixinUser> newList = new ArrayList <> ();
        for (WeixinUser user : listAll) {
            if (StringUtils.isNotBlank (user.getBind_name ())) {
                String bindName = user.getBind_name ().trim ().replace ("，", ",").replace (" ", "").replace ("￥", "").replace ("$", "").replace ("(奖)", "").replace ("（奖）", "").replace ("(预)", "").replace ("（预）", "").replace ("*", "").replaceAll ("[a-zA-Z]", "");

                // 包含,即多用户
                if (bindName.indexOf (",") != -1) {
                    String[] arrays = bindName.split (",");
                    for (String str : arrays) {
                        if (StringUtils.isNotBlank (str)) {
                            WeixinUser newUser = new WeixinUser ();
                            BeanUtils.copyProperties (user,newUser);
                            newUser.setBind_name (str);
                            newList.add (newUser);
                        }

                    }
                } else {
                    WeixinUser newUser = new WeixinUser ();
                    BeanUtils.copyProperties (user,newUser);
                    newList.add (newUser);
                }
                //长度超过9
                if (bindName.length () >= 9) {

                    WeixinUser newUser = new WeixinUser ();
                    BeanUtils.copyProperties (user,newUser);
                    newUser.setBind_name (bindName.substring (0, 8));
                    newList.add (newUser);
                }
            }
        }

        Map <String, List <WeixinUser>> map = new HashMap <> ();
        for (WeixinUser user : newList) {
            String bindName = user.getBind_name ();
            if (StringUtils.isNotBlank (bindName)) {
                if (map.containsKey (bindName)) {
                    List <WeixinUser> lst = map.get (bindName);
                    lst.add (user);
                } else {
                    List <WeixinUser> lst = new ArrayList <> ();
                    lst.add (user);
                    map.put (bindName, lst);
                }
            }

        }
        return map;
    }

    //俱乐部
    @Cacheable(cacheNames = "weixin_user", key = "'weixin_user_selectAllWithMapByClub'", sync = true)
    @Override
    public Map <String, List <WeixinUser>> selectAllWithMapByClub() {
        List <WeixinUser> listAll = selectAll ();

        List <WeixinUser> newList = new ArrayList <> ();
        for (WeixinUser user : listAll) {
            if (StringUtils.isNotBlank (user.getBind_name ()) && StringUtils.isNotBlank (user.getClub_bind_loft ())) {
                String bindName = user.getBind_name ().trim ().replace ("，", ",").replace (" ", "").replace ("￥", "").replace ("$", "").replace ("(奖)", "").replace ("（奖）", "").replace ("(预)", "").replace ("（预）", "").replace ("*", "").replaceAll ("[a-zA-Z]", "");
                String bindLoft = user.getClub_bind_loft ();// 棚号

                // 包含,即多用户
                if (bindName.indexOf (",") != -1) {
                    String[] arrays = bindName.split (",");
                    for (String str : arrays) {
                        if (StringUtils.isNotBlank (str)) {
                            WeixinUser newUser = new WeixinUser ();
                            BeanUtils.copyProperties (user,newUser );
                            newUser.setBind_name (str);
                            newList.add (newUser);
                        }
                    }
                } else {
                    WeixinUser newUser = new WeixinUser ();
                    BeanUtils.copyProperties (user,newUser);
                    newList.add (newUser);
                }
            }
        }

        Map <String, List <WeixinUser>> map = new HashMap <> ();
        for (WeixinUser user : newList) {
            String bindName = user.getBind_name ();
            String bindLoft = user.getClub_bind_loft ();// 棚号
            String key = bindName + "_" + bindLoft;
            if (map.containsKey (key)) {
                List <WeixinUser> lst = map.get (key);
                lst.add (user);
            } else {
                List <WeixinUser> lst = new ArrayList <> ();
                lst.add (user);
                map.put (key, lst);
            }
        }
        return map;
    }

    @Override
    public int delete(String id) {
        return weixinUserExtMapper.deleteByPrimaryKey (id);
    }

    @Override
    public int insertByList(List <WeixinUser> list) {
        return weixinUserExtMapper.insertByList (list);
    }

    @Override
    public int updateByList(List <WeixinUser> list) {
        return weixinUserExtMapper.updateByList (list);
    }


    @Override
    public int updateSubscribe() {
        return weixinUserExtMapper.updateSubscribe ();
    }

}
