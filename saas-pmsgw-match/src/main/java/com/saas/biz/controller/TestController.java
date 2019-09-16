package com.saas.biz.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.saas.biz.component.WeixinUserSync;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;
import com.saas.biz.pojo.NodejsCustomerMessage;
import com.saas.biz.pojo.PmsgwGameMaster;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.NodejsCrawlerService;
import com.saas.biz.service.NodejsCustomerMessageService;
import com.saas.biz.service.WeixinUserService;
import com.saas.biz.util.EmojiFilter;
import com.saas.common.BaseResponse;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 移动端相关操作
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/test")
public class TestController {
	private static final Logger loger = LoggerFactory.getLogger(TestController.class); 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "/test1",produces="text/html;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public String test1(String url) throws UnsupportedEncodingException {
		HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("text/html");
        headers.setContentType(type);
      
       
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<String>(null,headers);
        
		ParameterizedTypeReference<byte[]> typeRef = new ParameterizedTypeReference<byte[]>() {
		};
		ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, typeRef);
		String result = new String(responseEntity.getBody());
		result=StringUtils.replace(result, "<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n" + 
				"", "<link href=\"../../style/mo10/css/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n" + 
						"");
		loger.info(result);
		return result;
	}
	
@RequestMapping(value = "/test2",produces="text/html;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public String test2(String url) {
		get();
		return "ok";
	}
	/**
     * 发送 get请求
     */
    public void get() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet("http://gdgp.chinaxinge.com/style/mo10/default.asp?gp_id=1174");
            httpget.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            //System.out.println("============="+response.toString());
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    //System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("Response content: " + EntityUtils.toString(entity,"GBK"));
                }
                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Autowired
	private WeixinUserSync weixinUserSync;
    
    @RequestMapping(value ="/test3", method = RequestMethod.GET)
    @ResponseBody
    public String test3() throws Exception {
    	weixinUserSync.synchronize();
        return "ok";
    }
}
