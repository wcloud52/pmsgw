package com.saas.common;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

/**
 * web工具类;</br> 该类目前提供如下功能： <oi>分页参数等表单参数获取</oi> <oi>HttpServletRequest
 * request对象获取</oi>
 * 
 * @author yanlg
 */
public final class WebUtil {

    private static ThreadLocal<HttpServletRequest> _request = new ThreadLocal<HttpServletRequest>();

    public static HttpServletRequest getRequest() {
        return _request.get();
    }

    /**
     * 处理request对象来获取分页参数、客户端Locale信息等
     * 
     * @param request
     */
    public static void processRequest(HttpServletRequest request) {
        _request.set(request);
    }

    /**
     * 清除本线程对应的所有变量值
     */
    public static void removeAll() {
        _request.remove();
    }
    
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
    public  static void copyBodyToResponse(byte[] body, HttpServletResponse response) throws IOException {
		if (body.length > 0) {
			response.setContentLength(body.length);
			FileCopyUtils.copy(body, response.getOutputStream());
		}
	}
}
