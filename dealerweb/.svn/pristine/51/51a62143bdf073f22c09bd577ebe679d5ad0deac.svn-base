package com.ava.dealer.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.base.controller.Base4MvcController;
import com.ava.facade.IReportBaseExtractionFacade;
import com.ava.util.DateTime;

@Controller
@RequestMapping("/dealer/reportBase")
public class ReportBaseController extends Base4MvcController {
	
	protected final static Logger logger = Logger.getLogger(ReportBaseController.class);
	
	@Autowired
	private IReportBaseExtractionFacade reportBaseExtractionFacade;
	
	/**
	 * 通过设备号查询设备信息信息
	 */
	@RequestMapping(value = "/extraction.vti", method = RequestMethod.GET)
	public void extraction(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		Date starttime = new java.util.Date();
		logger.info("报表抽取开始时间：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
		
		reportBaseExtractionFacade.extraction(1, null, DateTime.toDate(startDate), DateTime.toDate(endDate));
		reportBaseExtractionFacade.updateReportBase(1, null, DateTime.toDate(startDate), DateTime.toDate(endDate));
		
		logger.info("报表抽取结束时间：" + DateTime.getNormalDateTime() + 
				" 耗时：" + DateTime.getSecondDifference(starttime, new java.util.Date()) + "秒");
	}
	
}
