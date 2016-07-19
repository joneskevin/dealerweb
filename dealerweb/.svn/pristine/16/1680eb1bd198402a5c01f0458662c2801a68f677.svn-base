package com.ava.resource;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import com.ava.back.service.ITimerTaskAtHourService;
import com.ava.util.DateTime;

public class MyHourTimerTask extends TimerTask{
	/**	当前小时数	*/
	private static int NOW_HOUR = 0;
	/**	当前一周中的天数	*/
	private static int NOW_DAY_OF_WEEK = 0;

	private static boolean isRunning = false;

	//private ServletContext context = null;
	
	ITimerTaskAtHourService timerTaskAtHourService = (ITimerTaskAtHourService) GlobalConfig.getBean("timerTaskAtHourService");

	public MyHourTimerTask() {
		//System.out.println("===================================开始构造 MyHourTimerTask()===================================");
	}
	
	public MyHourTimerTask(ServletContext context) {
		//System.out.println("===================================开始构造 MyHourTimerTask(ServletContext context)===================================");
		//this.context = context;
	}

	public void run() {
		Calendar cal = Calendar.getInstance();
		if (!isRunning) {
			isRunning = true;
			NOW_HOUR = cal.get(Calendar.HOUR_OF_DAY);
			NOW_DAY_OF_WEEK = cal.get(Calendar.DAY_OF_WEEK);

			//===================================== 每小时执行一次的定时计划任务 =====================================
			if ( true ) {
			}
			
			//===================================== 每2小时执行一次的定时计划任务 =====================================
			if ( NOW_HOUR%2 == 0 ){
			}
			
			//===================================== 每3小时执行一次的定时计划任务 =====================================
			if ( NOW_HOUR%3 == 0 ){
			}

			//===================================== 每天凌晨1点开始的定时计划任务 =====================================
			if ( NOW_HOUR == 1 ) {
			}

			//===================================== 每天凌晨2点开始的定时计划任务 =====================================
			if ( NOW_HOUR == 2 ) {
				Date  starttime = new java.util.Date();
				System.out.println("");
				System.out.println("======================= 开始执行指定任务（过期站内消息清除） 当前时间：" + DateTime.getNormalDateTime());
				timerTaskAtHourService.processShortMessage();
				System.out.println("======================= 指定任务执行结束（过期站内消息清除） 当前时间：" + DateTime.getNormalDateTime() + 
						"      耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
			}

			//===================================== 每天凌晨3点开始的定时计划任务 =====================================
			if ( NOW_HOUR == 3 ) {
			}

			//===================================== 每天凌晨4点开始的定时计划任务 =====================================
			if ( NOW_HOUR == 4 ) {				
			}

			//===================================== 每天凌晨5点开始的定时计划任务 =====================================
			if ( NOW_HOUR == 5 ) {
			}

			//===================================== 每周日凌晨6点开始的定时计划任务 =====================================
			if ( NOW_HOUR == 6 && NOW_DAY_OF_WEEK == 1 ) {
			}

			//===================================== 每周一凌晨6点开始的定时计划任务 =====================================
			if ( NOW_HOUR == 6 && NOW_DAY_OF_WEEK == 2 ) {
				Date  starttime = new java.util.Date();
				System.out.println("");
				System.out.println("======================= 开始执行指定任务（审批模块未关联数据清理） 当前时间：" + DateTime.getNormalDateTime());
				timerTaskAtHourService.processApproval();
				System.out.println("======================= 指定任务执行结束（审批模块未关联数据清理） 当前时间：" + DateTime.getNormalDateTime() + 
						"      耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
			}
			
			isRunning = false;
		} else {
			System.out.println("上一次任务执行还未结束");
		}
	}
}
