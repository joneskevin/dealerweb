package com.ava.back.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.back.service.IOperatorLogService;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.dao.IOperatorLogDao;
import com.ava.domain.entity.OperatorLog;
@Service
public class OperatorlogService implements IOperatorLogService{
	@Autowired
	private IOperatorLogDao operatorLogDao;

	@Override
	public List searchOperatorLog(TransMsg transMsg, OperatorLog operatorlog, String startTime, String endTime){
		// 按开始结束时间段查询
		ParameterUtil.appendTimeConfineQueryCondition(transMsg.getParameters(), "actionTime", startTime, endTime);
		
		List monitorUserLogs = operatorLogDao.findByPage(transMsg, " ORDER BY id DESC ");
		return monitorUserLogs;
	}
}
