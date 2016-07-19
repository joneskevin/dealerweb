package com.ava.dealer;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dealer.service.ITestDriveService;
import com.ava.domain.vo.TestDriveVO;
import com.ava.util.DateTime;

public class TestTestDrive extends TestBase {
	
	@Autowired
   private ITestDriveService testDriveService;

	/**
	 * 按时间段统计
	 */
	@Test
	public void testListTestDrive() {
		Date startTime = new java.util.Date();
		System.out.println("开始时间：" + DateTime.getNormalDateTime());
		//testDriveService.listTestDrive(new TransMsg(), new TestDriveVO(), startTime, endTime, 1, null, false);
		System.out.println("结束时间：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(startTime, new java.util.Date()) + "秒");
		
	}
}