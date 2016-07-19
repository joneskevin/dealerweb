package com.ava.admin.service;

import java.util.Map;

import com.ava.admin.domain.vo.TaskMessageLogVo;
import com.ava.admin.domain.vo.TaskMessageVo;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;

public interface IReHandleTestDriveService {

	/**
	 * 开始重跑
	 * @author tangqingsong
	 * @version 
	 * @param Map<String,Object> argMap
	 */
	public void reHandle(Map<String,Object> argMap);
	
	public void saveTaskMessage(TaskMessageVo taskMessageVo);
	
	public void updateTaskMessage(TaskMessageVo taskMessageVo);
	
	/**
	 * 分析延迟上报的重跑
	 * @author tangqingsong
	 * @version
	 */
	public void analyseByDelay();
	
	/**
	 * 分析无效试驾重跑
	 * @author tangqingsong
	 * @version
	 */
	public void analyseByInvalid();
	
	/**
	 * 分析4S店内重跑
	 * @author tangqingsong
	 * @version
	 */
	public void analyseByInCommpany();
	
	/**
	 * 更新日志状态为完成
	 * @author tangqingsong
	 * @version 
	 * @param taskMessageLogVo
	 */
	public void updateLogStatus(TaskMessageLogVo taskMessageLogVo);
	
	/**
	 * 运行错误的而导致的失败任务重新运行
	 * @author tangqingsong
	 * @version
	 */
	public void failTaskRehandle();
	
}
