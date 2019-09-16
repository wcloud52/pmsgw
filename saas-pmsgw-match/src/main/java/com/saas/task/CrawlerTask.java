package com.saas.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CrawlerTask {
	private static final Logger log = LoggerFactory.getLogger(CrawlerTask.class);
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Environment evn;
	public ResponseEntity<String> crawler(String database) {		
		String url = evn.getProperty(database);
		ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {};
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
		return responseEntity;
	}
}
