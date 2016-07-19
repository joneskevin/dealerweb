package com.ava.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.TestDrive;
import com.ava.domain.vo.TestDrive4StatVO;

public interface ITestDriveDao {
	public TestDrive get(Integer id);
	
	public List<TestDrive4StatVO> findByPage(String nativeSql, TransMsg transMsg) ;

	public List<TestDrive> findByPage(TransMsg msg, String additionalCondition);
	
	public List<TestDrive> find(Map parameters, String additionalCondition);
	
	public List executeSQLQuery(String nativeSql);
	
	public TestDrive getTestDriveByVehicle(Integer vehicleId);
	
	public Integer saveTestDrive(TestDrive testDrive);
	
	public void updateTestDrive(TestDrive testDrive);
	
	public void delTestDrive(TestDrive testDrive);
	
	/**
	 * 修改指定车辆的试驾记录为无效记录（报备审批通过后的操作）
	 *
	 * @author wangchao
	 * @version 
	 * @param filingProposalId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public int bulkUpdateTestDrive4DeclareManage(Integer filingProposalId, Date startTime, Date endTime);
	
	/**
	 * 取试驾开始时间以下的试驾
	 * @author tangqingsong
	 * @version 
	 * @param vehicleId
	 * @param startDate
	 * @return
	 */
	public TestDrive getTestDriveByVehicle(Integer vehicleId,String startDate);
}
