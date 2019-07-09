package com.ifp.weixin.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifp.weixin.entity.base.AccessToken;
import com.ifp.weixin.entity.base.AcessTokenManager;
import com.ifp.weixin.entity.jsapi.JsApiManager;


/**
 * 微信通用接口工具类
 * 
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class AcessTokenThread implements Runnable {    
    private static Logger log = LoggerFactory.getLogger(AcessTokenThread.class);    
    // 第三方用户唯一凭证    
    public static String appid = "";    
    // 第三方用户唯一凭证密钥    
    public static String appsecret = "";    
    public static AccessToken accessToken = null;    
    public static String jsapi_ticket="";
    public void run() {    
        while (true) {    
            try {    
                accessToken = AcessTokenManager.getAccessToken(appid, appsecret);    
               
                if (null != accessToken) {    
                	 jsapi_ticket=JsApiManager.getTicket(appid,appsecret,accessToken.getToken());
                    log.info("获取access_token成功，有效时长{}秒 token:{}/jsapi_ticket:{}", accessToken.getExpiresIn(), accessToken.getToken(),jsapi_ticket);    
                    // 休眠7000秒    
                    Thread.sleep((accessToken.getExpiresIn() - 200) * 1000); 
                    //Thread.sleep(60*1000);  
                } else {    
                    // 如果access_token为null，60秒后再获取    
                    Thread.sleep(60 * 1000);    
                }    
            } catch (InterruptedException e) {    
                try {    
                    Thread.sleep(60 * 1000);    
                } catch (InterruptedException e1) {    
                    log.error("{}", e1);    
                }    
                log.error("{}", e);    
            }    
        }    
    }    
}    