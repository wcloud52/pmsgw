package com.saas.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.saas.task.SendGameMessageAsyncTask;
import com.saas.task.SendMessageAsyncTask;

@Component
public class TaskScheduler {

	private static final Logger log = LoggerFactory.getLogger(TaskScheduler.class);

	public final static long Minute1 = 60000;
	public final static long Minute2 = 120000;
	public final static long Minute3 = 180000;
	public final static long Minute4 = 240000;
	public final static long Minute5 = 300000;
	public final static long Minute10 = 600000;
	public final static long Minute30 = 1800000;
	public final static long Minute60 = 3600000;
	

	@Autowired
	private SendGameMessageAsyncTask sendGameMessageAsyncTask;
	/**
	 * 中国鸽网赛事数据
	 */
	
	//@Scheduled(initialDelayString="${sendGameMessageTask.initialDelayString}",fixedDelayString="${sendGameMessageTask.fixedDelayString}")
	//@Scheduled(initialDelay=Minute1,fixedDelay=Minute10)
	@Scheduled(cron="0 0/10 5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20 * * ?")
	public void sendGameMessageTask() {
		log.debug(" >>sendGameMessageTask start....");
		//sendGameMessageAsyncTask.sendGameMessage();
		log.debug(" >>sendGameMessageTask end....");
	}
	
	
}
