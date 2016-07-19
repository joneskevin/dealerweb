package com.ava.dealer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Location;
import com.ava.domain.entity.TestDrive;
import com.ava.domain.vo.NoTestDriveVO;
import com.ava.domain.vo.TestDrive4StatVO;
import com.ava.domain.vo.TestDriveVO;

public interface ITestDriveService {
	
	/** 保存试驾路线*/
	public Integer saveTestDrive(TestDrive testDrive);
	
	public void updateTestDrive(TestDrive testDrive);
	
	public void delTestDrive(TestDrive testDrive);
	
	public List<TestDrive> getTestDriveList(Map parameters, String additionalCondition);
	
	public List<Location> getLocationList(HashMap parameters, String additionalCondition);
	
	public Location getLastLocation(HashMap parameters, String additionalCondition);

	/**
	 * 查询试驾列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param testDrive
	 * @param startTime TODO
	 * @param endTime TODO
	 * @param companyId
	 * @param userId 
	 * @param isExport
	 * @return
	 */
	public ResponseData listTestDrive(TransMsg transMsg, TestDriveVO testDrive, String startTime, String endTime, Integer companyId, Integer userId, boolean isExport);
	
	/**
	 * 查询连续无试驾列表
	 * @author liuq 
	 * @version 0.1 
	 * @param companyId
	 * @param season
	 * @return
	 */
	public List<TestDriveVO> listNoTestDrive(Integer companyId, Integer season);
	
	/**
	 * 查询连续无试驾列表【不启用】
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param noTestDrive
	 * @param companyId
	 * @param isExport
	 * @return
	 */
	public ResponseData listNoTestDriveOld(TransMsg transMsg, NoTestDriveVO noTestDrive, Integer companyId, boolean isExport);
	
	/**
	 * 查询连续无试驾列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param noTestDrive
	 * @param companyId
	 * @param isExport
	 * @return
	 */
	public ResponseData listNoTestDrive(TransMsg transMsg, NoTestDriveVO noTestDrive,Integer companyId, boolean isExport);
	
	/**
	 * 查询试驾统计[单周、月度、季度、时间段]
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param testDrive
	 * @param companyId
	 * @param userId
	 * @param isDetail 是否是明细
	 * @param isExport
	 * @return
	 */
	public ResponseData listTestDriveStatistic(TransMsg transMsg, TestDrive4StatVO testDrive, Integer companyId, Integer userId, boolean isDetail, boolean isExport);
	
}
