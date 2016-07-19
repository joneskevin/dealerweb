package com.ava.back.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.UserLog;

public interface IUserLogService {
	public List list(HttpServletRequest request);
	
	public List searchUserLog(TransMsg transMsg, UserLog userLog, String startTime, String endTime);
	
}
