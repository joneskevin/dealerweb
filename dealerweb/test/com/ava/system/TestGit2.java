
package com.ava.system;

import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JLabel;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dealer.service.IVehicleService;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.VehicleVO;
import com.ava.gateway.domain.vo.HeaderMessage;
import com.ava.gateway.facade.IProtocolParseBusinessFacade;
import com.ava.util.DateTime;

public class TestGit2 extends TestBase {
	@Autowired
	public IVehicleService service;
	
	@Autowired
	public IProtocolParseBusinessFacade protocolParseBusinessFacade;
	
	@Test
	@Ignore
	public void testQuery() {
		Date startTime=new Date();
		System.out.println("开始Time：" + DateTime.toNormalDotDateTime(startTime));
		service.listVehicle(new TransMsg(), new VehicleVO(), 1, 1, false);
		Date endTime = new java.util.Date();
		//输出结束时间
		System.out.println("end time：" + DateTime.toNormalDotDateTime(endTime)) ;
	}
	

	
}

