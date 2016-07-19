package com.ava.dealer;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.baseSystem.service.ICompanyService;
import com.ava.util.DateTime;

public class TestCompany extends TestBase {
	@Autowired
	private ICompanyService companyService;
	
	/**
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testInitDealerPoints() throws IOException {
		Date startTime=new Date();
		Date starttime = new java.util.Date();
		Date endTime = new java.util.Date();
		companyService.initDealerPoints();
		System.out.println("开始时间：" + DateTime.toNormalDotDateTime(startTime));
		System.out.println("结束时间：" + DateTime.toNormalDotDateTime(endTime)) ;
		System.out.println(" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
		
	}
	
}