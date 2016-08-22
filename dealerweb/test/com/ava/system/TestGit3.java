
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
/**
 * 我是bug分支添加的注释3
 * @author kevin
 *
 */
public class TestGit3 extends TestBase {
	@Autowired
	public IVehicleService service;
	
	@Autowired
	public IProtocolParseBusinessFacade protocolParseBusinessFacade;
	
	@Test
	@Ignore
	public void testQuery() {
		Date startTime=new Date();
		//输出开始时间
		System.out.println("startTime：" + DateTime.toNormalDotDateTime(startTime));
		service.listVehicle(new TransMsg(), new VehicleVO(), 1, 1, false);
		Date endTime = new java.util.Date();
		//输出结束时间
		System.out.println("end time：" + DateTime.toNormalDotDateTime(endTime)) ;
	}
	
	@Test
	@Ignore
	public void testUpdate() {
		Date startTime=new Date();
		//输出开始时间
		System.out.println("startTime：" + DateTime.toNormalDotDateTime(startTime));
		service.listVehicle(new TransMsg(), new VehicleVO(), 1, 1, false);
		Date endTime = new java.util.Date();
		//输出结束时间
		System.out.println("end time：" + DateTime.toNormalDotDateTime(endTime)) ;
	}

	@Test
	@Ignore
	public void testInsert() {
		Date startTime=new Date();
		//输出开始时间
		System.out.println("startTime：" + DateTime.toNormalDotDateTime(startTime));
		service.listVehicle(new TransMsg(), new VehicleVO(), 1, 1, false);
		Date endTime = new java.util.Date();
		//输出结束时间
		System.out.println("end time：" + DateTime.toNormalDotDateTime(endTime)) ;
	}

	

	
}

