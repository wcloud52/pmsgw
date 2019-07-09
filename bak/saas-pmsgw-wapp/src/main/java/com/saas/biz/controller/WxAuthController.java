package com.saas.biz.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.saas.biz.view.UserInfo;
import com.saas.biz.view.WxLoginInfo;
import com.saas.common.BaseResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wx/auth")
public class WxAuthController {
    private final Log logger = LogFactory.getLog(WxAuthController.class);


    @Autowired
    private WxMaService wxService;

   

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
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Object> loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
    	 logger.info(JSON.toJSONString(wxLoginInfo));
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if(code == null){
            return BaseResponse.ToJsonResult(-1);
        }

        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
            logger.info(JSON.toJSONString(result));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        if(sessionKey == null || openId == null){
        	 return BaseResponse.ToJsonResult(-1);
        }

       /* LitemallUser user = userService.queryByOid(openId);
        if(user == null){
            user = new LitemallUser();
            user.setUsername(userInfo.getNickName());  
            user.setPassword(openId);                  
            user.setWeixinOpenid(openId);
            user.setAvatar(userInfo.getAvatarUrl());
            user.setNickname(userInfo.getNickName());
            user.setGender(userInfo.getGender());
            user.setUserLevel((byte)0);
            user.setStatus((byte)0);
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.client(request));
            user.setAddTime(LocalDateTime.now());

            userService.add(user);
        }
        else{
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.client(request));
            userService.update(user);
        }
*/
        // token
      /*  
       * UserToken userToken = UserTokenManager.generateToken(openId);

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("userInfo", userInfo);
        */
        return  BaseResponse.ToJsonResult(userInfo);
    }

    
}
