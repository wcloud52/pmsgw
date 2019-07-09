package com.saas.biz.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.saas.biz.pojo.NodejsMpUser;
import com.saas.biz.service.NodejsMpUserService;
import com.saas.biz.util.IpUtil;
import com.saas.biz.util.ResponseUtil;
import com.saas.biz.util.UserTokenManager;
import com.saas.biz.util.bcrypt.BCryptPasswordEncoder;
import com.saas.biz.vo.UserInfo;
import com.saas.biz.vo.UserToken;
import com.saas.biz.vo.WxLoginInfo;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/auth")
public class WxAuthController {
    private final Log logger = LogFactory.getLog(WxAuthController.class);

    @Autowired
    private NodejsMpUserService nodejsMpUserService;

    @Autowired
    private WxMaService wxService;

    /**
     * 账号登录
     *
     * @param body 请求内容，{ username: xxx, password: xxx }
     * @param request 请求对象
     * @return 登录结果
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              token: xxx,
     *              tokenExpire: xxx,
     *              userInfo: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @RequestMapping("login")
    public Object login(@RequestBody String body, HttpServletRequest request) {
    	JSONObject jsonObject = JSONObject.parseObject(body);  
        String username =jsonObject.getString( "username"); 
        String password = jsonObject.getString( "password");
        if(username == null || password == null){
            return ResponseUtil.badArgument();
        }

        NodejsMpUser user =nodejsMpUserService.selectOneByUsername(username);
       

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(password, user.getPassword())){
            return ResponseUtil.fail(403, "账号密码不对");
        }

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        UserToken userToken = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    /**
     * 微信登录
     *
     * @param wxLoginInfo 请求内容，{ code: xxx, userInfo: xxx }
     * @param request 请求对象
     * @return 登录结果
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              token: xxx,
     *              tokenExpire: xxx,
     *              userInfo: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @RequestMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if(code == null || userInfo == null){
            return ResponseUtil.badArgument();
        }

        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        if(sessionKey == null || openId == null){
            return ResponseUtil.fail();
        }

        NodejsMpUser user = nodejsMpUserService.selectOneByWeixinOpenid(openId);
        if(user == null){
            user = new NodejsMpUser();
            user.setUsername(userInfo.getNickName());  // 其实没有用，因为用户没有真正注册
            user.setPassword(openId);                  // 其实没有用，因为用户没有真正注册
            user.setWeixin_openid(openId);
            user.setAvatar(userInfo.getAvatarUrl());
            user.setNickname(userInfo.getNickName());
            user.setGender(userInfo.getGender());
            user.setUser_level((byte)0);
            user.setStatus((byte)0);
            user.setLast_login_time(new Date());
            user.setLast_login_ip(IpUtil.client(request));
            user.setAdd_time(new Date());

            nodejsMpUserService.insert(user);
        }
        else{
        	user.setLast_login_time(new Date());
            user.setLast_login_ip(IpUtil.client(request));
            nodejsMpUserService.update(user);
        }

        // token
        UserToken userToken = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    /**
     * 账号注册
     *
     * @param body 请求内容
     *  {
     *      username: xxx,
     *      password: xxx,
     *      mobile: xxx
     *      code: xxx
     *  }
     *  其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              token: xxx,
     *              tokenExpire: xxx,
     *              userInfo: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("register")
    public Object register(@RequestBody String body, HttpServletRequest request) {
    	JSONObject jsonObject = JSONObject.parseObject(body);  
        String username =jsonObject.getString( "username"); 
        String password = jsonObject.getString( "password");
        
        String mobile = jsonObject.getString( "mobile");
        String code = jsonObject.getString( "code");

        if(username == null || password == null || mobile == null || code == null){
            return ResponseUtil.badArgument();
        }

        List<NodejsMpUser> userList =nodejsMpUserService.selectListByUsername(username);
        if(userList.size() > 0){
            return ResponseUtil.fail(403, "用户名已注册");
        }

        userList = nodejsMpUserService.selectListByMobile(mobile);
        if(userList.size() > 0){
            return ResponseUtil.fail(403, "手机号已注册");
        }
       
        NodejsMpUser user = new NodejsMpUser();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);

        user = new NodejsMpUser();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setMobile(mobile);
        user.setWeixin_openid("");
        user.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        user.setNickname(username);
        user.setGender((byte)0);
      
        user.setUser_level((byte)0);
        user.setStatus((byte)0);
        user.setLast_login_time(new Date());
        user.setLast_login_ip(IpUtil.client(request));
        user.setAdd_time(new Date());
        
        nodejsMpUserService.insert(user);


        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        UserToken userToken = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    /**
     * 账号密码重置
     *
     * @param body 请求内容
     *  {
     *      password: xxx,
     *      mobile: xxx
     *      code: xxx
     *  }
     *  其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     *   成功则 { errno: 0, errmsg: '成功' }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("reset")
    public Object reset(@RequestBody String body, HttpServletRequest request) {
    	
    	JSONObject jsonObject = JSONObject.parseObject(body);  
       
        String password = jsonObject.getString( "password");     
        String mobile = jsonObject.getString( "mobile");
        String code = jsonObject.getString( "code");
        
     
        if(mobile == null || code == null || password == null){
            return ResponseUtil.badArgument();
        }

        List<NodejsMpUser> userList = nodejsMpUserService.selectListByMobile(mobile);
        NodejsMpUser user = null;
        if(userList.size() > 1){
            return ResponseUtil.serious();
        }
        else if(userList.size() == 0){
            return ResponseUtil.fail(403, "手机号未注册");
        }
        else{
            user = userList.get(0);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);

        nodejsMpUserService.update(user);

        return ResponseUtil.ok();
    }
}
