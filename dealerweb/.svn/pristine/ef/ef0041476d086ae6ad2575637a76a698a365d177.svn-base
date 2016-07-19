package com.ava.dealer;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.facade.impl.NoTestDriveFacade;
import com.ava.util.DateTime;

public class TestNoTestDrive extends TestBase {
	
	@Autowired
	private NoTestDriveFacade noTestDriveFacade;
	
	/**
	 * 连续无试驾
	* Description: 
	* @author henggh 
	* @version 0.1
	 */
	@Test
	public void testNoTestDrive() {
		Date starttime = new java.util.Date();
		//noTestDriveFacade.extractionNoTestDrive(0, null);
		noTestDriveFacade.extractionNoTestDrive(1, DateTime.getSeason(new Date()));
		
		System.out.print("======================= 抽取【连续无试驾】数据  当前时间：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
	}
}