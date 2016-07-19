package com.ava.admin.service;

import java.util.Map;

import com.ava.admin.domain.vo.CurrentGpsMessageVo;
import com.ava.admin.domain.vo.TaskMessageLogVo;
import com.ava.admin.domain.vo.TaskMessageVo;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.vo.TestDriveVO;

public interface IAdminService {

	/**
	 * 查询gps坐标
	 * @author tangqingsong
	 * @version 
	 * @param transMsg
	 * @param currentGpsMessageVo
	 * @return
	 */
	public ResponseData findGpsPoint(TransMsg transMsg,CurrentGpsMessageVo currentGpsMessageVo);

	/**
	 * 查询试驾信息
	 * @author tangqingsong
	 * @param transMsg
	 * @param testDrive
	 * @param companyId
	 * @param userId
	 * @param isExport
	 * @return
	 */
	public ResponseData findTestDrive(TransMsg transMsg, TestDriveVO testDrive, Integer companyId, Integer userId, boolean isExport);

	public ResponseData findTaskMessage(TransMsg transMsg,TaskMessageVo taskMessageVo);

	/**
	 * 查询异常日志
	 * @author tangqingsong
	 * @version 
	 * @param transMsg
	 * @param taskMessageLogVo
	 * @return
	 */
	public ResponseData findLogMessage(TransMsg transMsg,TaskMessageLogVo taskMessageLogVo,boolean isExport);

	public void deleteLog(String id);
	
	/**
	 * 修复点火和熄火报文
	 * @author tangqingsong
	 * @version 
	 * @param argMap
	 */
	public void repairFireOnOrOut(Map<String, Object> argMap);
}
