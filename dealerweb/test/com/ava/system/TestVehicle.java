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

public class TestVehicle extends TestBase {
	@Autowired
	public IVehicleService service;
	
	@Autowired
	public IProtocolParseBusinessFacade protocolParseBusinessFacade;
	
	@Test
	@Ignore
	public void testQuery() {
		Date startTime=new Date();
		System.out.println("开始时间：" + DateTime.toNormalDotDateTime(startTime));
		service.listVehicle(new TransMsg(), new VehicleVO(), 1, 1, false);
		Date endTime = new java.util.Date();
		System.out.println("结束时间：" + DateTime.toNormalDotDateTime(endTime)) ;
	}
	
	@Test
	public void testHandleVehicleBox() {
		Date startTime=new Date();
		Date starttime = new java.util.Date();
		Date endTime = new java.util.Date();
		Integer type = 1;
		String vin = "LSVRS61T1E2029237";
		String uniqueId = "OBD00990478383";
		String simId = "56610001";
		service.handleVehicleBox(type, vin, uniqueId, simId);
		System.out.println("开始时间：" + DateTime.toNormalDotDateTime(startTime));
		System.out.println("结束时间：" + DateTime.toNormalDotDateTime(endTime)) ;
		System.out.println(" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
		
	}
	
	@Test
	@Ignore
	public void test111() {
		Date startTime=new Date();
		Date starttime = new java.util.Date();
		Date endTime = new java.util.Date();
		HeaderMessage header = new HeaderMessage();
		header.setSerialNumber("1111111111");
		header.setImsi("11111111");
		header.setVin("LSVG066R1E2118462");
		//String msg = "{\"header\":{\"vin\":\"LSVG066R1E2118462\",\"serialNumber\":\"41030404878\",\"imsi\":\"460040062200296\",\"messageCode\":1281,\"timestamp\":\"2015-05-04 16:56:58\"},\"body\":{\"data\":{\"fireState\":1,\"lng\":119.947464,\"lat\":31.753182,\"baiduLng\":\"119.95873311448\",\"baiduLat\":\"31.756800589082\",\"heading\":238,\"speed\":0.0,\"ko3Kilometer\":17.905}}}";
		
		
		//protocolParseBusinessFacade.handleMessage(msg);
		System.out.println("开始时间：" + DateTime.toNormalDotDateTime(startTime));
		System.out.println("结束时间：" + DateTime.toNormalDotDateTime(endTime)) ;
		System.out.println(" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
		
	}
	
	public static void main(String[] args) {
		int[] array = new int[]{20,40,90,30,80,70,50};
		
		
		int tmp,j;
		for (int i = 1; i < array.length; i++) {
			tmp = array[i];
			for (j = i -1; j>=0 && array[j] > tmp;j--) {
				array[j + 1] = array[j];
			}
			array[j + 1] = tmp;
		}
		
		for(int i = 0;i<array.length; i ++) {
			System.out.println(array[i]+"\t");
		}
		String a = "dddd1dddd1";
		System.out.println(a.replace("1", "X"));
		 
		 
	}
	

	
	
	
}