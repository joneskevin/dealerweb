package com.ava.dealer;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.base.dao.IHibernateDao;
import com.ava.domain.vo.ProposalCompanyVehcileInfo;
import com.ava.facade.IPoposalFacade;
import com.ava.facade.impl.ReportBaseFacade;
import com.ava.resource.GlobalConstant;
import com.ava.util.DateTime;

public class TestProposal extends TestBase {
	@Autowired
	public IPoposalFacade poposalFacade;
	@Autowired
	private ReportBaseFacade reportBaseFacade;
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Test
	public void testConfirmInstallationResult() {
		ProposalCompanyVehcileInfo proposalCompanyVehcileInfo = new ProposalCompanyVehcileInfo();
		//此设备未安装至车辆上
		proposalCompanyVehcileInfo.getTbox().setUniqueId("312321321321312");
		
		//您输入的设备号未在上汽大众试乘试驾车机设备库中
		proposalCompanyVehcileInfo.getTbox().setUniqueId("312321321321313");
		
		//车机设备安装成功, 经销商：" + dbVehicle.getCompanyName() + ",vin:" + dbVehicle.getVin() + ",设备号:" + dbBox.getUniqueId()
		proposalCompanyVehcileInfo.getTbox().setUniqueId("0112");
		
		//此车辆未安装车机设备
		proposalCompanyVehcileInfo.getVehicle().setPlateNumber("京C426K9");
		
		//您输入的车辆信息未在上汽大众试乘试驾车辆库中
		proposalCompanyVehcileInfo.getVehicle().setPlateNumber("沪C988G3");
		
		//此车辆已成功安装车机设备,经销商：" + dbVehicle.getCompanyName() + ",vin:" + dbVehicle.getVin() + ",设备号:" + dbBox.getUniqueId()
		proposalCompanyVehcileInfo.getVehicle().setPlateNumber("沪C916G1");
		
		//poposalFacade.confirmInstallationResult(proposalCompanyVehcileInfo);
		
		//testDriveRateFacade.addTestDriveRate();
		Date starttime = new java.util.Date();
		logger.debug("======================= 抽取数据  当前时间：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
		System.out.print("======================= 抽取数据  当前时间：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		//DateTime.initDayList(DateTime.toDate("2012-06-18"), new Date());
		String dateStr = "2014-12-03" + " 00:00:00";
		 String sql = " SELECT COUNT(1) FROM t_vehicle t1 ,t_box_operation t2 WHERE t1.COMPANY_ID = t2.COMPANY_ID " +
			 	  " AND t1.id = t2.VEHICLE_ID " +
			 	  " AND t1.CAR_STYLE_ID = " + 1 + 
			 	  " AND t1.CONFIGURE_TYPE = " + GlobalConstant.CONFIGURE_TYPE_MUST +
			 	  " AND t1.COMPANY_ID = " + 1 + 
			 	  " AND t2.TYPE = " + GlobalConstant.PROPOSAL_TYPE_INSTALLATION + 
			 	  " AND t2.OPERATION_TIME = ' " + dateStr +" ' ";
			 	  
	   //List vehicleList = hibernateDao.executeSQLQueryList(sql);
		 
		 String dateStr1 = "2014-07-08";
		 String startTime = dateStr1 + " 00:00:00";
		 String endTime = dateStr1 + " 23:59:59";
		 sql = " SELECT COUNT(t1.ID) FROM t_violation t1, t_vehicle t2  WHERE t1.COMPANY_ID = t2.COMPANY_ID " +
					     " AND t2.CAR_STYLE_ID = " + 1 +
					     " AND t1.CREATION_TIME BETWEEN DATE(' " + startTime + " ') " + " AND DATE(' " +  endTime +" ')";

		//List<Integer> violationList = hibernateDao.executeSQLQueryList(sql);
		//Integer count = violationList.get(0);
		
		
		
	
		
	}
}