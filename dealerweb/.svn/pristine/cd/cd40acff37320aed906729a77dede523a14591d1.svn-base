package com.ava.back.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ava.back.service.IUserLogService;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.controller.Base4MvcController;
import com.ava.domain.entity.UserLog;

@Controller
@RequestMapping("/back/userLog")
public class UserLogController extends Base4MvcController {
	@Autowired
	private IUserLogService service;
    
    @RequestMapping(value="/search.vti", method=RequestMethod.GET)
	public String searchQuestion(TransMsg transMsg, UserLog monitorUserLog) {
    	String startTime = getStringParameter("startTime");
    	String endTime = getStringParameter("endTime");
		List monitorUserLogList = service.searchUserLog(transMsg, monitorUserLog, startTime, endTime);
		getRequest().setAttribute("userLogs", monitorUserLogList);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		return "/back/backSystem/userLog/listUserLog";
	}
	
}
