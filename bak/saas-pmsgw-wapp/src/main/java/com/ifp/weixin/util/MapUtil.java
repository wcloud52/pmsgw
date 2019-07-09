package com.ifp.weixin.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Map 工具类
 * 
 */
public class MapUtil {

	public static void mapToObject(Map<String, Object> map, Object obj) {  
		try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(obj, value);  
                }  
  
            }  
  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }  
  
        return;  
    }  
	       
	    public static Map<?, ?> objectToMap(Object obj) throws Exception {  
	    	 if(obj == null)  
	             return null;      
	    
	         Map<String, Object> map = new HashMap<String, Object>();   
	    
	         BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
	         PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
	         for (PropertyDescriptor property : propertyDescriptors) {    
	             String key = property.getName();    
	             if (key.compareToIgnoreCase("class") == 0) {   
	                 continue;  
	             }  
	             Method getter = property.getReadMethod();  
	             Object value = getter!=null ? getter.invoke(obj) : null;  
	             map.put(key, value);  
	         }    
	    
	         return map;  
	    }    
	       

}
