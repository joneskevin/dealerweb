package com.ava.dealer;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.facade.impl.ReportBaseExtractionFacade;
import com.ava.util.DateTime;

public class TestReportBaseExtraction extends TestBase {
	@Autowired
	private ReportBaseExtractionFacade reportBaseExtractionFacade;
	
	/**
	 * 抽取前一天
	 */
	@Test
	public void testExtraction() {
		Date startTime=new Date();
		Date starttime = new java.util.Date();
		reportBaseExtractionFacade.extraction(0, 1, null, null);
		Date endTime = new java.util.Date();
		reportBaseExtractionFacade.updateReportBase(0, 1, null, null);
		System.out.println("开始时间：" + DateTime.toNormalDotDateTime(startTime));
		System.out.println("结束时间：" + DateTime.toNormalDotDateTime(endTime)) ;
		System.out.println(" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
		
	}
	
	/**
	 * 通过时间段抽取
	 */
	@Test
	@Ignore
	public void testExtractionForDate() {
		Date starttime = new java.util.Date();
		System.out.println("开始时间：" + DateTime.getNormalDateTime());
		Date startDate = DateTime.toDate("2015-01-01");
		Date endDate = DateTime.toDate("2015-01-29");
		reportBaseExtractionFacade.extraction(1, null, startDate, endDate);
		System.out.println("======================= 抽取数据  当前时间：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
	}
	
	/**
	 * 通过时间段抽取更新
	 */
	@Test
	@Ignore
	public void testUpdateReportBaseForDate() {
		Date starttime = new java.util.Date();
		System.out.println("开始时间：" + DateTime.getNormalDateTime());
		Date startDate = DateTime.toDate("2015-01-01");
		Date endDate = DateTime.toDate("2015-01-29");
		reportBaseExtractionFacade.updateReportBase(1, null, startDate, endDate);
		System.out.println("======================= 抽取数据  当前时间：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
	}
	
}