package com.ava.quartz.routine;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.baseSystem.service.ICompanyService;
import com.ava.dealer.service.ITestDriveService;
import com.ava.dealer.service.IViolationService;
import com.ava.domain.entity.Location;
import com.ava.domain.entity.TestDrive;
import com.ava.facade.INoTestDriveFacade;
import com.ava.facade.IReportBaseExtractionFacade;
import com.ava.resource.GlobalConfig;
import com.ava.resource.RestTemplateUtil;
import com.ava.resource.cache.CacheTestDriveManager;
import com.ava.util.DateTime;

/**
 * 禁止同一任务并发执行
 * Title: Class RoutineJob.java
 * Copyright: Copyright(c)2014
 * Company: BDC
 * @author liuq
 * @version 0.1
 */
@DisallowConcurrentExecution 
public class RoutineJob implements Job {
	protected final static Logger logger = Logger.getLogger(RestTemplateUtil.class);
	
	private INoTestDriveFacade noTestDriveFacade = (INoTestDriveFacade) GlobalConfig.getBean("noTestDriveFacade");
	private IReportBaseExtractionFacade reportBaseExtractionFacade = (IReportBaseExtractionFacade) GlobalConfig.getBean("reportBaseExtractionFacade");
	private ICompanyService companyService = (ICompanyService) GlobalConfig.getBean("companyService");

	@Autowired
	ITestDriveService testDriveService=(ITestDriveService)GlobalConfig.getBean("testDriveService");//试驾
	
	@Autowired
	IViolationService violationService=(IViolationService)GlobalConfig.getBean("violationService"); //违规
	/**
	 * 执行例行任务
	 * @author liuq 
	 * @version 0.1 
	 * @param arg0
	 * @throws JobExecutionException
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		Date starttime = new java.util.Date();
		logger.info("======================= extract report_base beginTime：" + DateTime.getNormalDateTime() + 
				"      耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		//executeInit();//首次初始化数据
		executeDay();//每天抽取
		
		//handleTestException();
		
		logger.info("======================= extract report_base endTime：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
	}
	
	/**
	 * t_report_base报表数据初始化
	 * @throws JobExecutionException
	 */
	public void executeInit() throws JobExecutionException {
		
		//按时间段抽取---START---
		Date startDate = DateTime.toDate("2015-01-01");
		Date endDate = DateTime.toDate("2015-04-27");
		//抽取基础数据
		reportBaseExtractionFacade.extraction(1, null, startDate, endDate);
		//更新业务数据
		reportBaseExtractionFacade.updateReportBase(1, null, startDate, endDate);
		//按时间段抽取---END---
	}
	
	/**
	 * 每天执行抽取基础报表数据
	 * @throws JobExecutionException
	 */
	public void executeDay() throws JobExecutionException {
		//连续无试驾数据, 抽取当前季度的
		//noTestDriveFacade.extractionNoTestDrive(1, DateTime.getSeason(new Date()));
		//报表基础数据，抽取前一天的所有数据
		reportBaseExtractionFacade.extraction(0, 1, null, null);
		//更新前一天的业务数据
		reportBaseExtractionFacade.updateReportBase(0, 1, null, null);
	}
	
	/**
	 * 初始化经销商位置点
	 * @throws JobExecutionException
	 */
	public void executeInitDealerPoint() throws JobExecutionException, IOException{
		companyService.initDealerPoints();
	}

	/**
	 * 处理因GPS数据本身造成试驾线路无法结束的问题
	 * @throws JobExecutionException
	 */
	public void handleTestException() throws JobExecutionException{
		Date date=new Date();
		Map<String,Integer> testDriveParameters=new HashMap<String,Integer>();
		testDriveParameters.put("currentStatus", 0);
		List<TestDrive> testDrives=testDriveService.getTestDriveList(testDriveParameters,null);
		if(!(null==testDrives || testDrives.isEmpty())){
			for(TestDrive testDrive:testDrives){
				Location location=testDriveService.getLastLocation(null, " and creationTime>="+ DateTime.toShortDateTime(testDrive.getStartTime()));
				if(null!=location && DateTime.getSecondDifferenceNew(DateTime.getDate(location.getCreationTime().toString(), DateTime.PATTERN_SHORTDATETIME),date) >=600){
					testDrive.setCurrentStatus(1);
					testDrive.setEndTime(DateTime.getDate(location.getCreationTime().toString(), DateTime.PATTERN_SHORTDATETIME));
					testDriveService.updateTestDrive(testDrive);
					CacheTestDriveManager.removeTestDriveById(testDrive.getVehicleId(),null);
					CacheTestDriveManager.removeTestDriveGpsPoint(testDrive.getVehicleId(),null);
					CacheTestDriveManager.removeTestViolation(testDrive.getVehicleId(),null);
				}
			}
		}
		violationService.handleNoEndViolation();
	}
}
