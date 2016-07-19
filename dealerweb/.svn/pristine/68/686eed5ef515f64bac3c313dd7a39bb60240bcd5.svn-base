package com.ava.admin.dao.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.admin.dao.IReHandleTestDriveDao;
import com.ava.admin.domain.entity.TaskMessage;
import com.ava.admin.domain.entity.TaskMessageLog;
import com.ava.admin.domain.vo.TaskMessageLogVo;
import com.ava.admin.domain.vo.TaskMessageVo;
import com.ava.base.dao.IHibernateDao;
import com.ava.domain.vo.LocationVO;
import com.ava.domain.vo.TestDriveInfoVO;
import com.ava.domain.vo.TestDriveVO;
import com.ava.domain.vo.TestNoDriveWeekVO;
import com.ava.domain.vo.ViolationVO;
import com.ava.resource.GlobalConstant;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;

@Repository("reHandleTestDriveDao")
public class ReHandleTestDriveDaoImpl implements IReHandleTestDriveDao {

	@Autowired
	private IHibernateDao hibernateDao;
	private Logger loger=Logger.getLogger(ReHandleTestDriveDaoImpl.class);
	private int batchSize = 200;
	
	/**
	 * 查询试驾实体对象 
	 * @author tangqingsong
	 * @version 
	 * @param argMap
	 * @return
	 */
	public List<TestDriveVO> findTestDriveByList(Map<String,Object> argMap){
		//备份试驾数据
		StringBuffer sql = new StringBuffer(" SELECT id,"+
					" COMPANY_ID as companyId, "+
					" DEPARTMENT_ID as departmentId, "+
					" VEHICLE_ID as vehicleId, "+
					" STATUS as status, "+
					" TEST_DRIVE_DATE as testDriveDateTime, "+
					" START_TIME as startTime, "+
					" END_TIME as endTime, "+
					" INTERVAL_TIME as intevalTime, "+
					" LINE_ID as lineId, "+
					" LOOP_COUNT as loopCount, "+
					" MILEAGE as mileage, "+
					" SAVE_DATE as saveDate, "+
					" CURRENT_STATUS as currentStatus, "+
					" POINT_FLAG as pointFlag "+
					" FROM "+
					" t_test_drive t "+
					" WHERE 1=1 ");
		if(argMap.containsKey("vehicleId")){
			sql.append(" and t.VEHICLE_ID=:vehicleId ");
		}
		if(argMap.containsKey("startDate")){
			sql.append(" and t.START_TIME>=:startDate ");
		}
		if(argMap.containsKey("endDate")){
			sql.append(" and t.START_TIME<=:endDate ");
		}			
		List<TestDriveVO> result = hibernateDao.executeSQLQueryVoList(sql.toString(),TestDriveVO.class,argMap);
		return result;
	}
	
	/**
	 * 批量保存试驾数据到历史表
	 */
	public List<Long> saveBatchTestDrive(List<TestDriveVO> testDriveList){
		//备份试驾数据
		StringBuffer sql = new StringBuffer("INSERT t_test_drive_history ( "+
					" COMPANY_ID, "+
					" DEPARTMENT_ID, "+
					" VEHICLE_ID, "+
					" STATUS, "+
					" TEST_DRIVE_DATE, "+
					" START_TIME, "+
					" END_TIME, "+
					" INTERVAL_TIME, "+
					" LINE_ID, "+
					" LOOP_COUNT, "+
					" MILEAGE, "+
					" SAVE_DATE, "+
					" CURRENT_STATUS, "+
					" POINT_FLAG "+
					" ) VALUES ");
		List<Long> idList = new LinkedList<>();
		StringBuffer values = new StringBuffer();
		for(int i=0;i<testDriveList.size();i++){
			TestDriveVO testDrive = testDriveList.get(i);
			idList.add(new Long(testDrive.getId()));
			values.append("(");
			values.append(testDrive.getCompanyId()).append(", ");
			values.append(testDrive.getDepartmentId()).append(", ");
			values.append(testDrive.getVehicleId()).append(", ");
			values.append(testDrive.getStatus()).append(",");
			String testDriveDateTime = testDrive.getTestDriveDateTime()==null?"":DateTime.getDate(testDrive.getTestDriveDateTime(), DateTime.PATTERN_DATE);
			values.append("'").append(testDriveDateTime).append("', ");
			String startTime = testDrive.getStartTime()==null?"":DateTime.getDate(testDrive.getStartTime(), DateTime.PATTERN_DATETIME);
			values.append("'").append(startTime).append("', ");
			String endTime = testDrive.getEndTime()==null?"":DateTime.getDate(testDrive.getEndTime(), DateTime.PATTERN_DATETIME);
			values.append("'").append(endTime).append("', ");
			values.append(testDrive.getSecondsOfInterval()).append(", ");
			values.append(testDrive.getLineId()).append(", ");
			values.append(testDrive.getLoopCount()).append(", ");
			values.append(testDrive.getMileage()).append(", ");
			String saveDate = testDrive.getSaveDate()==null?"":DateTime.getDate(testDrive.getSaveDate(), DateTime.PATTERN_DATETIME);
			values.append("'").append(saveDate).append("', ");
			values.append(testDrive.getCurrentStatus()).append(", ");
			values.append(testDrive.getPointFlag());	
			values.append(")");
			if(i==(testDriveList.size()-1) || (i!=0 && i%batchSize==0)){
				hibernateDao.executeSQLUpdate(sql.toString()+values.toString());
				values = new StringBuffer();
				continue;
			}
			values.append(",");
		}
		return idList;
	}
	
	/**
	 * 批量根据ID删除表中的数据
	 */
	public void deleteBatchById(String tableName,List<Long> idList){
		//备份试驾数据
		StringBuffer sql = new StringBuffer("delete from "+tableName+"  where id in (");
		StringBuffer values = new StringBuffer();
		for(int i=0;i<idList.size();i++){
			Long id = idList.get(i);
			values.append(id.longValue());
			if(i==(idList.size()-1) || (i!=0 && i%batchSize==0)){
				values.append(")");
				hibernateDao.executeSQLUpdate(sql.toString()+values.toString());
				values = new StringBuffer();
				continue;
			}
			values.append(",");
		}
	}

	/**
	 * 查询违规数据
	 * @author tangqingsong
	 * @version 
	 * @param argMap
	 */
	public List<ViolationVO> findViolationByList(Map<String,Object> argMap){
		//备份试驾数据
		StringBuffer sql = new StringBuffer("SELECT id, "+
				" COMPANY_ID as companyId, "+
				" DEPARTMENT_ID as departmentId, "+
				" VEHICLE_ID as vehicleId, "+
				" TYPE as typeId, "+
				" COUNT as countId, "+
				" CREATION_TIME as creationTime, "+
				" DRIVE_LINE_ID as driveLineId, "+
				" START_TIME as startTime, "+
				" END_TIME as endTime, "+
				" CURRENT_STATUS as currentStatus, "+
				" DELETION_FLAG as deletionFlag "+
				" FROM "+
				" t_violation t where 1=1 ");
		if(argMap.containsKey("vehicleId")){
			sql.append(" and VEHICLE_ID=:vehicleId ");
		}
		if(argMap.containsKey("startDate")){
			sql.append(" and START_TIME>=:startDate ");
		}
		if(argMap.containsKey("endDate")){
			sql.append(" and START_TIME<=:endDate ");
		}	
		List<ViolationVO> result = hibernateDao.executeSQLQueryVoList(sql.toString(),ViolationVO.class,argMap);
		return result;
	}
	
	
	/**
	 * 批量保存违规数据到历史表
	 * @author tangqingsong
	 * @version 
	 * @param violation
	 */
	public List<Long> saveBatchViolation(List<ViolationVO> violationList){
		//备份试驾数据
		StringBuffer sql = new StringBuffer("INSERT t_violation_history ( "+
					" COMPANY_ID, "+
					" DEPARTMENT_ID, "+
					" VEHICLE_ID, "+
					" TYPE, "+
					" COUNT, "+
					" CREATION_TIME, "+
					" DRIVE_LINE_ID, "+
					" START_TIME, "+
					" END_TIME, "+
					" CURRENT_STATUS, "+
					" DELETION_FLAG "+
					" ) VALUES ");
		List<Long> idList = new LinkedList<>();
		StringBuffer values = new StringBuffer();
		for(int i=0;i<violationList.size();i++){
			ViolationVO violation = violationList.get(i);
			idList.add(new Long(violation.getId()));
			values.append("(");
			values.append(violation.getCompanyId()).append(", ");
			values.append(violation.getDepartmentId()).append(", ");
			values.append(violation.getVehicleId()).append(", ");
			values.append(violation.getTypeId()).append(", ");
			values.append(violation.getCountId()).append(", ");
			String creationTime = violation.getCreationTime()==null?"":DateTime.getDate(violation.getCreationTime(), DateTime.PATTERN_DATETIME);
			values.append("'").append(creationTime).append("', ");
			values.append(violation.getDriveLineId()).append(", ");
			String startTime = violation.getStartTime()==null?"":DateTime.getDate(violation.getStartTime(), DateTime.PATTERN_DATETIME);
			values.append("'").append(startTime).append("', ");
			String endTime = violation.getEndTime()==null?"":DateTime.getDate(violation.getEndTime(), DateTime.PATTERN_DATETIME);
			values.append("'").append(endTime).append("', ");
			values.append(violation.getCurrentStatus()).append(", ");
			values.append(violation.getDeletionFlag());	
			values.append(")");
			if(i==(violationList.size()-1) || (i!=0 && i%batchSize==0)){
				hibernateDao.executeSQLUpdate(sql.toString()+values.toString());
				values = new StringBuffer();
				continue;
			}
			values.append(",");
		}
		return idList;
	}
	
	/**
	 * 查询定位数据
	 * @author tangqingsong
	 * @version 
	 * @param argMap
	 */
	public List<LocationVO> findLocationByList(Map<String,Object> argMap){
		//备份试驾数据
		StringBuffer sql = new StringBuffer("SELECT  ID as id,"+
					 " VEHICLE_ID as vehicleId, "+
					 " ORIGN_LONGITUDE as orignLongitude, "+
					 " ORIGN_LATITUDE as orignLatitude, "+
					 " LONGITUDE as longitude, "+
					 " LATITUDE as latitude, "+
					 " SPEED as speed, "+
					 " DIRECTION as direction, "+
					 " CREATION_TIME as creationTime, "+
					 " SAVE_DATE as saveDate "+
					 " FROM "+
					 " t_location t where 1=1 ");
		if(argMap.containsKey("vehicleId")){
			sql.append(" and VEHICLE_ID=:vehicleId ");
		}
		if(argMap.containsKey("startDateLong")){
			sql.append(" and CREATION_TIME>=:startDateLong ");
		}
		if(argMap.containsKey("endDateLong")){
			sql.append(" and CREATION_TIME<=:endDateLong ");
		}						 
		List<LocationVO> result = hibernateDao.executeSQLQueryVoList(sql.toString(),LocationVO.class,argMap);
		return result;
	}
	
	/**
	 * 批量保存定位数据到历史库
	 * @author tangqingsong
	 * @version 
	 * @param location
	 */
	public List<Long> saveBatchLocation(List<LocationVO> locationList){
		//备份试驾数据
		StringBuffer sql = new StringBuffer("INSERT t_location_history ( "+
					 " VEHICLE_ID, "+
					 " ORIGN_LONGITUDE, "+
					 " ORIGN_LATITUDE, "+
					 " LONGITUDE, "+
					 " LATITUDE, "+
					 " SPEED, "+
					 " DIRECTION, "+
					 " CREATION_TIME, "+
					 " SAVE_DATE "+
					 " ) VALUES ");
		List<Long> idList = new LinkedList<>();
		StringBuffer values = new StringBuffer();
		for(int i=0;i<locationList.size();i++){
			LocationVO location = locationList.get(i);
			idList.add(Long.valueOf(location.getId()));
			values.append("(");
			values.append("'").append(location.getVehicleId()).append("', ");
			values.append("'").append(location.getOrignLongitude()).append("', ");
			values.append("'").append(location.getOrignLatitude()).append("', ");
			values.append("'").append(location.getLongitude()).append("', ");
			values.append("'").append(location.getLatitude()).append("', ");
			values.append(location.getSpeed()).append(", ");
			values.append(location.getDirection()).append(", ");
			values.append(location.getCreationTime()).append(", ");
			String saveDate = location.getSaveDate()==null?"":DateTime.getDate(location.getSaveDate(), DateTime.PATTERN_DATETIME);
			values.append("'").append(saveDate).append("'");
			values.append(")");
			if(i==(locationList.size()-1) || (i!=0 && i%batchSize==0)){
				hibernateDao.executeSQLUpdate(sql.toString()+values.toString());
				values = new StringBuffer();
				continue;
			}
			values.append(",");
		}
		return idList;
	}
	
	public void saveTaskMessage(TaskMessageVo taskMessageVo){
		TaskMessage task = new TaskMessage();
		MyBeanUtils.copyPropertiesContainsDate(task,taskMessageVo);
		Integer id = (Integer) hibernateDao.save(task);
		taskMessageVo.setId(id);
		loger.info("重跑-保存任务："+taskMessageVo.getTaskName()+",startDate:"+taskMessageVo.getStartTime()+
				",endDate:"+taskMessageVo.getEndTime()+",任务ID："+id);

	}
	
	public void updateTaskMessage(TaskMessageVo taskMessageVo){
		TaskMessage task = hibernateDao.get(TaskMessage.class, taskMessageVo.getId());
		if(task!=null){
			MyBeanUtils.copyPropertiesContainsDate(task,taskMessageVo);
			hibernateDao.update(task);
			loger.info("重跑-更新任务："+taskMessageVo.getTaskName()+",任务ID："+task.getId()+",startDate:"+taskMessageVo.getStartTime()+
					",endDate:"+taskMessageVo.getEndTime()+",状态："+task.getStatus());
		}
	}
	
	
	public void updateCompleteRate(TaskMessageVo taskMessageVo){
		if(taskMessageVo==null){
			return ;
		}
		StringBuffer sql = new StringBuffer("update t_task_message set ");
		sql.append(" COMPLETE_RATE=:completeRate, ");	
		sql.append(" DURATION_TIME=:durationTime,  ");	
		sql.append(" COMPLETE_ESTIMATE=:completeEstimate");	
		sql.append(" where id=:taskId");	
		Map<String,Object> argMap = new HashMap<String,Object>();
		argMap.put("taskId", taskMessageVo.getId());
		argMap.put("durationTime", taskMessageVo.getDurationTime());
		argMap.put("completeEstimate", taskMessageVo.getCompleteEstimate());
		hibernateDao.executeSQLUpdate(sql.toString(), argMap);
		loger.info("重跑-更新任务："+taskMessageVo.getTaskName()+",任务ID："+taskMessageVo.getId()+",startDate:"+taskMessageVo.getStartTime()+
				",endDate:"+taskMessageVo.getEndTime()+",状态："+taskMessageVo.getStatus());
	}
	
	public List<TestDriveInfoVO> findTestDrive(Map<String,Object> argMap){
		StringBuffer sql = new StringBuffer("select  " +
				" v.vin,t.START_TIME as startTime,t.END_TIME as endTime,t.vehicle_id as vehicleId ");
				sql.append(" from t_test_drive t,t_vehicle v  where t.VEHICLE_ID = v.id ");
				sql.append(" and t.CURRENT_STATUS=1");
		if(argMap.containsKey("status")){
			sql.append(" and t.`STATUS`=:status ");
		}
		if(argMap.containsKey("startTime")){
			sql.append(" and t.`START_TIME`>=:startTime ");
		}
		if(argMap.containsKey("endTime")){
			sql.append(" and t.`START_TIME`<=:endTime ");
		}	
		if(argMap.containsKey("intervalTime")){
			//sql.append(" and (t.`MILEAGE`>=:mileage ");
				sql.append(" and t.`INTERVAL_TIME`>=:intervalTime ");
		//	sql.append(")");
		}

		List<TestDriveInfoVO> result = hibernateDao.executeSQLQueryVoList(sql.toString(), TestDriveInfoVO.class,argMap);
		return result;
	}
	
	public List<TestDriveInfoVO> findTestDriveByEndTimeIsNull(Map<String,Object> argMap){
		StringBuffer sql = new StringBuffer("select " +
				" v.vin,t.START_TIME as startTime,t.END_TIME as endTime,t.vehicle_id as vehicleId ");
				sql.append(" from t_test_drive t,t_vehicle v where t.VEHICLE_ID = v.id ");
				sql.append(" and t.CURRENT_STATUS=0");
		sql.append(" and t.`START_TIME`>=:startTime ");
		sql.append(" and t.`END_TIME` is null ");
		List<TestDriveInfoVO> result = hibernateDao.executeSQLQueryVoList(sql.toString(), TestDriveInfoVO.class,argMap);
		return result;
	}
	
	public void saveTaskMessageLog(TaskMessageLogVo taskMessageLogVo){
		TaskMessageLog log = new TaskMessageLog();
		MyBeanUtils.copyPropertiesContainsDate(log,taskMessageLogVo);
		hibernateDao.save(log);
	}
	
	public void saveOrUpdateTaskMessageLog(TaskMessageLogVo taskMessageLogVo){
		if(taskMessageLogVo==null){
			return ;
		}
		Map<String,Object> argMap = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("update t_task_message_log set ");
		sql.append(" END_DATE=:endDate, ");	
		sql.append(" START_DATE=:startDate, ");	
		sql.append(" COUNTER=COUNTER+1, ");	
		sql.append(" CURTIME=:curtime where 1=1 ");	
		argMap.put("startDate", taskMessageLogVo.getStartDate());
		argMap.put("endDate", taskMessageLogVo.getEndDate());
		argMap.put("curtime",taskMessageLogVo.getCurtime());
		if(taskMessageLogVo.getId()!=null){
			sql.append(" and id=:id");	
			argMap.put("id", taskMessageLogVo.getId());
		}else{
			sql.append(" and vin=:vin");	
			sql.append(" and LOG_CODE=:logCode");	
			sql.append(" and status=:status");	
			argMap.put("vin", taskMessageLogVo.getVin());
			argMap.put("logCode", taskMessageLogVo.getLogCode());
			argMap.put("status", taskMessageLogVo.getStatus());
		}
		int result = hibernateDao.executeSQLUpdate(sql.toString(), argMap);
		if(result==0){
			this.saveTaskMessageLog(taskMessageLogVo);
		}
	}
	
	public List<TaskMessageLogVo> findLogMessage(Map<String,Object> argMap) {
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		//任务信息
		exeSql.append(" t.id, "+
				    " t.CURTIME as curtime, "+
					" t.vin as vin, "+
					" t.START_DATE as startDate, "+
					" t.END_DATE as endDate "+
					" from t_task_message_log t where 1=1 ");		
		if (argMap.containsKey("startDate")) {
			exeSql.append(" AND t.CURTIME >=:startDate ");
		}
		if (argMap.containsKey("endDate")) {
			exeSql.append(" AND t.CURTIME <=:endDate");
		}
		if(argMap.containsKey("logCode")){
			exeSql.append(" AND t.LOG_CODE =:logCode");
		}
		if(argMap.containsKey("status")){
			exeSql.append(" AND t.status =:status");
		}
		if(argMap.containsKey("vin")){
			exeSql.append(" AND t.vin =:vin");
		}
		if(argMap.containsKey("serialNumber")){
			exeSql.append(" AND t.SERIAL_NUMBER =:serialNumber");
		}			
		exeSql.append(" order by t.CURTIME desc");

		List<TaskMessageLogVo> logList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), TaskMessageLogVo.class,argMap);
		return logList;
	}
	
	
	public void updateTaskMessageLogStatus(TaskMessageLogVo taskMessageLogVo){
		if(taskMessageLogVo==null && taskMessageLogVo.getId()!=null){
			return ;
		}
		Map<String,Object> argMap = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("update t_task_message_log set ");
		sql.append(" status=:status, ");	
		sql.append(" CURTIME=:curtime where 1=1 ");	
		argMap.put("status", taskMessageLogVo.getStatus());
		argMap.put("curtime",taskMessageLogVo.getCurtime());
		sql.append(" and id=:id");	
		argMap.put("id", taskMessageLogVo.getId());
		hibernateDao.executeSQLUpdate(sql.toString(), argMap);
	}
	
	public List<TestNoDriveWeekVO> findVialdNotActiveTestDrive(Map<String,Object> argMap){
		StringBuffer sql = new StringBuffer();
			sql.append("SELECT w.id as id ");
			sql.append("FROM t_test_no_drive_week w, ");
			sql.append("(SELECT ");
			sql.append("t.VEHICLE_ID, ");
			sql.append("t.`STATUS`, ");
			sql.append("weekofyear(SUBDATE(DATE_ADD(t.START_TIME,INTERVAL -1 DAY),date_format(DATE_ADD(t.START_TIME,INTERVAL -1 DAY),'%w')-1)) ");
			sql.append(" AS WEEK ");
			sql.append("FROM ");
			sql.append("t_test_drive t ");
			sql.append("WHERE ");
			sql.append("t.VEHICLE_ID = :vehicleId ");
			sql.append("AND t.`STATUS` = 1 ");
			sql.append("AND t.CURRENT_STATUS = 1 ");
			sql.append("and t.START_TIME>=:startDate ");
			sql.append("and t.START_TIME<=:endDate ");
			sql.append(") v1 ");
			sql.append("WHERE ");
			sql.append("v1.VEHICLE_ID = w.VEHICLE_ID ");
			sql.append("AND v1.`week` = w.`WEEK` ");
			sql.append("group by w.ID ");
		List<TestNoDriveWeekVO> result = hibernateDao.executeSQLQueryVoList(sql.toString(), TestNoDriveWeekVO.class,argMap);
		return result;
	}
	
	public List<TaskMessageVo> findFailTaskMessage() {
		StringBuffer exeSql = new StringBuffer(" SELECT id, ");
		exeSql.append(" t.START_TIME as startTime, "+
				    " t.END_TIME as endTime, "+
					" t.vin as vin, "+
					" t.CREATE_TIME as createTime, "+
					" t.TASK_TYPE as taskType, "+
					" t.STATUS as status, "+
					" t.COUNTER as counter "+
					" from t_task_message t where counter<3 and" +
					" t.status="+GlobalConstant.TASK_STAUTS_ERROR);				
		exeSql.append(" order by t.CREATE_TIME ASC");
		List<TaskMessageVo> taskList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), TaskMessageVo.class);
		return taskList;
	}
	
}
