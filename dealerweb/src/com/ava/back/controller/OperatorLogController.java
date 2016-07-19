package com.ava.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ava.back.service.IOperatorLogService;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.controller.Base4MvcController;
import com.ava.domain.entity.OperatorLog;
@Controller
@RequestMapping("/back/operatorLog")
public class OperatorLogController  extends Base4MvcController{
	@Autowired
	private IOperatorLogService service;
    
    @RequestMapping(value="/search.vti", method=RequestMethod.GET)
	public String searchQuestion(TransMsg transMsg, OperatorLog operatorlog) {
    	String startTime = getStringParameter("startTime");
    	String endTime = getStringParameter("endTime");
    	
    	List operatorlogList = service.searchOperatorLog(transMsg, operatorlog, startTime, endTime);
    	getRequest().setAttribute("operatorlogs", operatorlogList);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		return "/back/backSystem/operatorLog/listOperatorLog";
	}
}
