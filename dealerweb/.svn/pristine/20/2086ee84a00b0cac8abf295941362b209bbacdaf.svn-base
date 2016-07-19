package com.ava.quartz.alert;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ava.back.service.ITimerTaskAtMinuteService;
import com.ava.resource.GlobalConfig;
import com.ava.util.DateTime;

@DisallowConcurrentExecution //禁止同一任务并发执行
public class MessageAlertJob implements Job {
	
	private static final Logger logger = Logger.getLogger(MessageAlertJob.class);
	
	private ITimerTaskAtMinuteService timerTaskAtMinuteService = (ITimerTaskAtMinuteService) GlobalConfig.getBean("timerTaskAtMinuteService");

	/**执行提醒任务*/
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Date starttime = new java.util.Date();
		logger.debug("");
		logger.debug("======================= 开始执行指定任务（待办事宜短信提醒） 当前时间：" + DateTime.getNormalDateTime());
		timerTaskAtMinuteService.processPendingTask();
		logger.debug("======================= 指定任务执行结束（待办事宜短信提醒） 当前时间：" + DateTime.getNormalDateTime() + 
				"      耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
	}

}
