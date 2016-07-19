package com.ava.admin.dao;

import java.util.List;
import java.util.Map;

import com.ava.admin.domain.vo.TaskMessageLogVo;
import com.ava.admin.domain.vo.TaskMessageVo;
import com.ava.domain.vo.LocationVO;
import com.ava.domain.vo.TestDriveInfoVO;
import com.ava.domain.vo.TestDriveVO;
import com.ava.domain.vo.TestNoDriveWeekVO;
import com.ava.domain.vo.ViolationVO;

public interface IReHandleTestDriveDao {

	/**
	 * 查询试驾实体对象 
	 * @author tangqingsong
	 * @version 
	 * @param argMap
	 * @return
	 */
	public List<TestDriveVO> findTestDriveByList(Map<String,Object> argMap);
	
	
	/**
	 * 批量保存试驾数据到历史表
	 */
	public List<Long> saveBatchTestDrive(List<TestDriveVO> testDriveList);
	
	/**
	 * 批量根据ID删除表中的数据
	 */
	public void deleteBatchById(String tableName,List<Long> idList);
	
	/**
	 * 查询违规数据
	 * @author tangqingsong
	 * @version 
	 * @param argMap
	 */
	public List<ViolationVO> findViolationByList(Map<String,Object> argMap);
	
	/**
	 * 批量保存违规数据到历史表
	 * @author tangqingsong
	 * @version 
	 * @param violation
	 */
	public List<Long> saveBatchViolation(List<ViolationVO> violationList);
	
	/**
	 * 查询定位数据
	 * @author tangqingsong
	 * @version 
	 * @param argMap
	 */
	public List<LocationVO> findLocationByList(Map<String,Object> argMap);
	
	/**
	 * 批量保存定位数据到历史库
	 * @author tangqingsong
	 * @version 
	 * @param location
	 */
	public List<Long> saveBatchLocation(List<LocationVO> locationList);
	
	
	/**
	 * 保存任务
	 * @author tangqingsong
	 * @version 
	 * @param taskMessageVo
	 */
	public void saveTaskMessage(TaskMessageVo taskMessageVo);
	
	/**
	 * 更新任务
	 * @author tangqingsong
	 * @version 
	 * @param taskMessageVo
	 */
	public void updateTaskMessage(TaskMessageVo taskMessageVo);
	
	/**
	 * 更新进度
	 * @author tangqingsong
	 * @version 
	 * @param taskMessageVo
	 */
	public void updateCompleteRate(TaskMessageVo taskMessageVo);
	
	public List<TestDriveInfoVO> findTestDrive(Map<String,Object> argMap);

	public void saveTaskMessageLog(TaskMessageLogVo taskMessageLogVo);
	
	public List<TestDriveInfoVO> findTestDriveByEndTimeIsNull(Map<String,Object> argMap);

	public void saveOrUpdateTaskMessageLog(TaskMessageLogVo taskMessageLogVo);
	
	public List<TaskMessageLogVo> findLogMessage(Map<String,Object> argMap);
	
	public void updateTaskMessageLogStatus(TaskMessageLogVo taskMessageLogVo);
	
	/**
	 * 根据车辆查询有效的非活跃试驾
	 * @author tangqingsong
	 * @version 
	 * @param vehicleId
	 * @return
	 */
	public List<TestNoDriveWeekVO> findVialdNotActiveTestDrive(Map<String,Object> argMap);

	/**
	 * 查询运行错误的任务
	 * @author tangqingsong
	 * @version 
	 * @return
	 */
	public List<TaskMessageVo> findFailTaskMessage(); 
}
