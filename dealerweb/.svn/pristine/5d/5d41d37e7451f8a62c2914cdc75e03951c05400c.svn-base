package com.ava.dealer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.dealer.service.IMonitorService;
import com.ava.domain.vo.Tree;
import com.ava.resource.SessionManager;

@Controller
@RequestMapping("/dealer/monitor")
public class MonitorController extends Base4MvcController {
	@Autowired
	private IMonitorService monitorService;
	
	@Autowired
	private IUserMenuService userMenuService;

	@RequestMapping(value = "/home.vti", method = RequestMethod.GET)
	public String displayHome() {
		/** Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd  = monitorService.displayHome(userId, companyId);
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		getRequest().setAttribute("dhtmlXtreeXML", rd.get("dhtmlXtreeXML")); */
		
		ResponseData rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		
		return "/dealer/monitor/monitorVehicle";
	}

	@RequestMapping(value = "/findVehicle.vti", method = RequestMethod.GET)
	public void findVehicle() {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		
		Integer orgId = getIntegerParameter("orgId");
		String plateNumber = getStringParameter("plateNumber");
		String vehicleIds = getStringParameter("vehicleIds");
		ResponseData rd = monitorService.findVehicle(companyId, orgId, plateNumber, vehicleIds);

		writeRd(rd);
	}
	
	/**
	 * 根据经销商ID获得车辆位置信息
	 * @param companyId
	 */
	@RequestMapping(value = "/getVehicleByDealerId.vti", method = RequestMethod.GET)
	public void getVehicleByDealerId(@RequestParam("companyId") Integer companyId) {
		ResponseData rd = monitorService.getVehicleByDealerId(companyId);
		writeRd(rd);
	}

	@RequestMapping(value = "/getVehicle.vti", method = RequestMethod.GET)
	public void getVehicle(@RequestParam("vehicleId") Integer vehicleId) {
		ResponseData rd = monitorService.getVehicle(vehicleId);

		writeRd(rd);
	}
	
	/**
	 * 异步加载实时监控的树
	 * @param tree
	 */
	@RequestMapping(value = "/queryTree.vti")
	public void queryTree(Tree tree){
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = monitorService.findTree(tree, companyId, userId);
		List<Tree> treeList = (List<Tree>) rd.get("treeList");
		writeRd(treeList);
	}
    
}
