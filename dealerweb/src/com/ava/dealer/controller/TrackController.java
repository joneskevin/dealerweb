package com.ava.dealer.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.domain.ResponseData;
import com.ava.dealer.service.ITrackService;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.util.DateTime;
import com.ava.util.MyStringUtils;
import com.ava.util.ValidatorUtil;

@Controller
@RequestMapping("/dealer/track")
public class TrackController extends Base4MvcController {
	@Autowired
	private ITrackService trackService;

	@RequestMapping(value = "/home.vti", method = RequestMethod.GET)
	public String displayHome(@RequestParam(value= "userMenu", required = false) Integer userMenu) {
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		String plateNumber = getStringParameter("plateNumber");
		getRequest().setAttribute("plateNumber", plateNumber);
		String startTime = getStringParameter("startTime");
		getRequest().setAttribute("startTime", startTime == null ? DateTime.getNormalDateTime() : startTime);
		String endTime = getStringParameter("endTime");
		getRequest().setAttribute("endTime", endTime == null ? DateTime.getNormalDateTime() : endTime);
		
		ResponseData rd = new ResponseData(GlobalConstant.FALSE);
		siteMenuType(rd);
		
		return "/dealer/track/playTrack";
	}

	@RequestMapping(value = "/findVehicle.vti", method = RequestMethod.POST)
	public @ResponseBody ResponseData findVehicle() {
		ResponseData rd = new ResponseData(0);

		String plateNumber = getStringParameter("q");
		if (MyStringUtils.isBlank(plateNumber)) {
			rd.setMessage("车牌不能为空");
			rd.setCode(-1);//车牌支持模糊查询，至少一个字符
		} else {
			rd = trackService.findVehicle(plateNumber);
		}

		return rd;
	}

	/**
	 * 加载经销商围栏
	 */
	@RequestMapping(value = "/findDriveLine.vti", method = RequestMethod.GET)
	public @ResponseBody ResponseData findDriveLine() {
		ResponseData rd = new ResponseData(0);
		
		String plateNumber = getStringParameter("plateNumber");
		rd = trackService.findDriveLine(plateNumber);
		
		return rd;
	}
	
	/**
	 * 轨迹播放
	 */
	@RequestMapping(value = "/findLocation.vti", method = RequestMethod.GET)
	public @ResponseBody ResponseData findLocation() {
		ResponseData rd = new ResponseData(0);
		try{
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
	
			String plateNumber = getStringParameter("plateNumber");
			String startTime = getStringParameter("startTime");
			String endTime = getStringParameter("endTime");
			if (!checkFindLocation(rd, plateNumber, startTime, endTime)) {
				rd.setCode(-1);//查询参数无效
			}else{
				Long startTimeL = DateTime.toShortDateTimeL(startTime);
				Long endTimeL = DateTime.toShortDateTimeL(endTime);
				rd = trackService.findLocation(companyId, plateNumber, startTimeL, endTimeL);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return rd;
	}

	private boolean checkFindLocation(ResponseData rd, String plateNumber, String startTime, String endTime) {
		if (MyStringUtils.isBlank(plateNumber)) {
			rd.setMessage("车牌不能为空");
			return false;
		} else if (!ValidatorUtil.isCivilPlateNum(plateNumber)) {
			rd.setMessage("车牌格式无效");
			return false;
		}

		if (MyStringUtils.isBlank(startTime)) {
			rd.setMessage("开始时间不能为空");
			return false;
		} else {
			Long startTimeL = DateTime.toShortDateTimeL(startTime);
			if (startTimeL == null) {
				rd.setMessage("开始时间格式无效");
				return false;
			}
		}
		
		Date dStartTime = DateTime.toDate(startTime);
		Date dEndTime = DateTime.toDate(endTime);
		

		if (MyStringUtils.isBlank(endTime)) {
			rd.setMessage("结束时间不能为空");
			return false;
		} else {
			Long endTimeL = DateTime.toShortDateTimeL(endTime);
			if (endTimeL == null) {
				rd.setMessage("结束时间格式无效");
				return false;
			}
		}
		
		long seconds = DateTime.getSecondDifference(dStartTime, dEndTime);
		if(seconds <= 0){
			rd.setMessage("开始时间不能大于等于结束时间");
			return false;
		}
		if(DateTime.getSecondDifference(DateTime.toDate(startTime), DateTime.toDate(endTime)) > 3600 * 24 * 100){
			rd.setMessage("时间跨度不能超过100天");
			return false;
		}
		
		return true;
	}
}
