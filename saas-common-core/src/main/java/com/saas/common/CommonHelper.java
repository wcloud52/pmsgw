package com.saas.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

public class CommonHelper {

	/**
	 * 根据逾期天数获取逾期类型
	 * @param days
	 * @return
	 */
	public static Integer getOverdueType(Integer days) {
		if(days==null)
			return null;
		for (int i = 0; i < 25; i++) {
			int v = i * 30;
			if (days <= v) {
				return i;
			}
		}
		return 25;
	}
	/**
	 * 从list中随机抽取元素 
	 * @param list
	 * @param n
	 * @return
	 */
	public static  List createRandomList(List list, int n) {
        // TODO Auto-generated method stub
        Map<Integer,String> map = new HashMap<Integer,String>();
        List listNew = new ArrayList();
        if (list.size() <= n) {
            return list;
        } 
        else {
            while (map.size() < n) {
                int random = (int) (Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    listNew.add(list.get(random));
                }
            }
            return listNew;
        }
    }
	/**
	 * 通过租户计算数据库连接源类型
	 * @param tenantId
	 * @return
	 */
	public static String getDataSourceType(String tenantId) {
		int tenantInt=NumberUtils.toInt(tenantId);
		tenantInt=(tenantInt-1)/10+1;
		String db="ds"+String.valueOf(tenantInt);
		return db;
	}
}
