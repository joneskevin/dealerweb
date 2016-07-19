package com.ava.dealer;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.facade.impl.ReportBaseFacade1;
import com.ava.util.DateTime;

public class TestReportBase1 extends TestBase {
	
	@Autowired
	private ReportBaseFacade1 reportBaseFacade;
	
	/**
	 * 统计前一天
	 */
	@Test
	public void testExtraction() {
		Date starttime = new java.util.Date();
		reportBaseFacade.extraction(0, 1, null, null);
		
		System.out.print("======================= 抽取数据  当前时间：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
	}
	
	/**
	 * 按时间段统计
	 */
	@Test
	@Ignore
	public void testExtractionByDate() {
		Date startTime=new Date();
		Date starttime = new java.util.Date();
		Date startDate = DateTime.toDate("2015-01-04");
	    Date endDate = DateTime.toDate("2015-01-04");
		reportBaseFacade.extraction(1, null, startDate, endDate);
		Date endTime = new java.util.Date();
		System.out.println("开始时间：" + DateTime.toNormalDotDateTime(startTime));
		System.out.println("结束时间：" + DateTime.toNormalDotDateTime(endTime)) ;
		System.out.println(" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
	}
}