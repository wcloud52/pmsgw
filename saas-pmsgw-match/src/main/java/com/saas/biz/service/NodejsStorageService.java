package com.saas.biz.service;

import com.saas.biz.pojo.NodejsStorage;

public interface NodejsStorageService extends BaseService<NodejsStorage,Integer> {

	NodejsStorage selectOneByKey(String key);

}
