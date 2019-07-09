package com.saas.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.saas.task.YunfeichinacomAsyncTask;

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
	


	/**
	 * 中国鸽网赛事数据
	 */
	
	//@Scheduled(initialDelayString="${crawlerSpeedpigeoncncomAsyncTask.initialDelayString}",fixedDelayString="${crawlerSpeedpigeoncncomAsyncTask.fixedDelayString}")
	public void crawlerSpeedpigeoncncomAsyncTask() {
		log.debug(" >>crawlerSpeedpigeoncncomAsyncTask start....");
		
		log.debug(" >>crawlerSpeedpigeoncncomAsyncTask end....");
	}
	
	@Autowired
	private YunfeichinacomAsyncTask yunfeichinacomAsyncTask;
	//@Scheduled(initialDelayString="${crawlerYunfeichinacomAsyncTask.initialDelayString}",fixedDelayString="${crawlerYunfeichinacomAsyncTask.fixedDelayString}")
	public void crawlerYunfeichinacomAsyncTask() {
		log.debug(" >>crawlerYunfeichinacomAsyncTask start....");
		yunfeichinacomAsyncTask.crawler();
		log.debug(" >>crawlerYunfeichinacomAsyncTask end....");
	}
}
