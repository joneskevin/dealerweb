package com.ava.back.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.back.service.IUserLogService;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.dao.IUserLogDao;
import com.ava.domain.entity.UserLog;
@Service
public class UserLogService  implements IUserLogService{
	@Autowired
	private IUserLogDao userLogDao;
	@Override
	public List list(HttpServletRequest request) {
		TransMsg transMsg = new TransMsg();
		transMsg.setPageSize(10);
	
		List operators = userLogDao.findByPage(transMsg,"order by id asc");
		return operators;
		
	}
	@Override
	public List searchUserLog(TransMsg transMsg, UserLog userLog, String startTime, String endTime){
		// 按开始结束时间段查询
		ParameterUtil.appendTimeConfineQueryCondition(transMsg.getParameters(), "actionTime", startTime, endTime);
		
		List monitorUserLogs = userLogDao.findByPage(transMsg, " ORDER BY id DESC ");
		return monitorUserLogs;
	}
}
