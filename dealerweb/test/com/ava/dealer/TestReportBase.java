package com.ava.dealer;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dealer.service.impl.TestDriveService;
import com.ava.domain.vo.TestDriveVO;
import com.ava.facade.impl.NoTestDriveFacade;
import com.ava.facade.impl.ReportBaseFacade;
import com.ava.quartz.moreThread.ExtractionData;
import com.ava.util.DateTime;

public class TestReportBase extends TestBase {
	
	@Autowired
	private IHibernateDao hibernateDao;
	@Autowired
	private ReportBaseFacade reportBaseFacade;
	@Test
	@Ignore
	public void testMoreThreadExtraction() {
		Date starttime = new java.util.Date();
		
		 //第一月开始时间
		String[] date1 = DateTime.getMonthAarry(1);
		Date startDate1 = DateTime.toDate(date1[0]);
	    Date endDate1 = DateTime.toDate(date1[1]);
		ExtractionData e1 = new ExtractionData("线程1", reportBaseFacade, 1, null, startDate1, endDate1, 1);
        Thread thread1= new Thread(e1);
        thread1.start();
        
       //第二月开始时间
  		String[] date2 = DateTime.getMonthAarry(2);
  		Date startDate2 = DateTime.toDate(date2[0]);
  	    Date endDate2 = DateTime.toDate(date2[1]);
        ExtractionData e2 = new ExtractionData("线程2", reportBaseFacade, 1, null, startDate2, endDate2, 1);
        Thread thread2 = new Thread(e2);
        thread2.start();
        
        //第三月开始时间
        String[] date3 = DateTime.getMonthAarry(3);
        Date startDate3 = DateTime.toDate(date3[0]);
        Date endDate3 = DateTime.toDate(date3[1]);
        ExtractionData e3 = new ExtractionData("线程3", reportBaseFacade, 1, null, startDate3, endDate3, 1);
        Thread thread3 = new Thread(e3);
        thread3.start();
        
        //第四月开始时间
        String[] date4 = DateTime.getMonthAarry(4);
        Date startDate4 = DateTime.toDate(date4[0]);
        Date endDate4 = DateTime.toDate(date4[1]);
        ExtractionData e4 = new ExtractionData("线程4", reportBaseFacade, 1, null, startDate4, endDate4, 1);
        Thread thread4 = new Thread(e4);
        thread4.start();
        
        System.out.print("======================= 抽取数据  当前时间：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
        
	}
	
	/**
	 * 统计前一天
	 */
	@Test
	@Ignore
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